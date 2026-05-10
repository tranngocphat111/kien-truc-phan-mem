import http from "k6/http";
import { check, sleep, group } from "k6";
import { BASE_URL, options as globalOptions } from "./config.js";
import { randomUserId, randomProductId } from "./utils.js";

/**
 * ============================================
 * 🛠️ INTERNAL SCENARIOS (Hàm mô phỏng API)
 * ============================================
 */

// 1. Browse Products
function browse(baseUrl) {
  const res = http.get(`${baseUrl}/products`);
  check(res, {
    "browse status is 200": (r) => r.status === 200,
  });
  return res;
}

// 2. Add to Cart
function addToCart(baseUrl, userId, productId) {
  const res = http.post(
    `${baseUrl}/cart/add`,
    JSON.stringify({ userId, productId, quantity: 1 }),
    { headers: { "Content-Type": "application/json" } }
  );
  check(res, {
    "add to cart status is 200": (r) => r.status === 200,
  });
  return res;
}

// 3. Checkout
function checkout(baseUrl, userId) {
  const res = http.post(
    `${baseUrl}/checkout`,
    JSON.stringify({ userId }),
    { headers: { "Content-Type": "application/json" } }
  );
  check(res, {
    "checkout handled": (r) => r.status === 200 || r.status === 400,
  });
  return res;
}

/**
 * ================================
 * 🎯 K6 LOAD TEST CONFIGURATION
 * ================================
 *
 * Script này mô phỏng hệ thống flash sale thực tế với 3 loại test:
 *
 * 1. LOAD TEST (flash_sale)
 *    → kiểm tra hệ thống chịu được bao nhiêu user
 *
 * 2. SPIKE TEST (spike)
 *    → kiểm tra khi traffic tăng đột ngột (flash sale mở)
 *
 * 3. SOAK TEST (soak)
 *    → kiểm tra hệ thống chạy lâu có ổn định không
 *
 * Đồng thời mô phỏng hành vi user thật:
 * browse → add to cart → checkout
 */

export const options = {
  ...globalOptions,
  scenarios: {

    /**
     * ============================================
     * 🔥 1. FLASH SALE - LOAD TEST
     * ============================================
     *
     * 👉 Mô phỏng traffic tăng dần như flash sale thật
     */
    flash_sale: {
  executor: "ramping-vus",
  startVUs: 0,
  stages: [
    { duration: "15s", target: 500 },
    { duration: "15s", target: 1000 },
    { duration: "15s", target: 2000 },
    { duration: "30s", target: 3000 },
    { duration: "30s", target: 4000 },
    { duration: "30s", target: 5000 },

    // 🔥 PEAK = 6000 USERS
    { duration: "30s", target: 6000 },
    { duration: "1m", target: 6000 },

    // 📉 scale down
    { duration: "30s", target: 4000 },
    { duration: "20s", target: 1000 },
    { duration: "10s", target: 0 },
  ],
  gracefulRampDown: "10s",
},

    /**
     * ============================================
     * ⚡ 2. SPIKE TEST
     * ============================================
     */
    spike: {
      executor: "ramping-arrival-rate",
      startRate: 10,
      timeUnit: "1s",
      preAllocatedVUs: 1000,
      maxVUs: 3000,
      stages: [
        { duration: "5s", target: 2000 },
{ duration: "5s", target: 3000 },
{ duration: "10s", target: 3000 },
      ],
      startTime: "3m",
    },

    /**
     * ============================================
     * 🧪 3. SOAK TEST
     * ============================================
     */
    soak: {
      executor: "constant-vus",
      vus: 2000,
      duration: "1m",
      startTime: "4m",
    },
  },
};

/**
 * ============================================
 * 👤 USER BEHAVIOR SIMULATION
 * ============================================
 */
export default function () {
  const userId = randomUserId();
  const productId = randomProductId();

  group("User Journey", function () {
    /**
     * 🛍️ Step 1: Browse
     * → stress Redis (read-heavy)
     */
    browse(BASE_URL);

    // ⏳ giả lập user suy nghĩ
    sleep(Math.random() * 2 + 1);

    /**
     * 🛒 Step 2: Add to Cart (40%)
     * → stress Redis write
     */
    if (Math.random() < 0.6) {
      addToCart(BASE_URL, userId, productId);

      sleep(Math.random() * 2 + 1);

      /**
       * 💳 Step 3: Checkout (20% của 40%)
       * → ~8% total user checkout
       * → stress: Inventory, MQ, DB
       */
      if (Math.random() < 0.4) {
        checkout(BASE_URL, userId);
        sleep(Math.random() * 1);
      }
    }
  });
}