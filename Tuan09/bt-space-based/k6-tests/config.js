// Link api gateway
export const BASE_URL = "http://192.168.1.62:8080/api";

// Cấu hình chung
export const options = {
  thresholds: {
    http_req_duration: ["p(95)<500"], // Tỉ lệ thời gian phản hồi < 500ms
    http_req_failed: ["rate<0.01"],   // Tỉ lệ lỗi < 1%
  },
};
