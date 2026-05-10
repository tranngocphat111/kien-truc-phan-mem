import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css'; // Import file CSS thuần

const API_BASE_URL = "http://localhost:8080/api";

function App() {
  const [foods, setFoods] = useState([]);
  const [cart, setCart] = useState([]);
  const [customerName, setCustomerName] = useState("");
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    axios.get(`${API_BASE_URL}/foods`).then(res => setFoods(res.data));
    axios.get(`${API_BASE_URL}/orders`).then(res => setOrders(res.data));
  }, []);

  const addToCart = (food) => setCart([...cart, food]);
  const removeFromCart = (index) => setCart(cart.filter((_, i) => i !== index));

  const placeOrder = () => {
    if (!customerName || cart.length === 0) return alert("Nhập tên và chọn món!");
    axios.post(`${API_BASE_URL}/orders?name=${customerName}`, cart.map(f => f.id))
      .then(() => {
        alert("Thành công!");
        setCart([]); setCustomerName(""); 
        axios.get(`${API_BASE_URL}/orders`).then(res => setOrders(res.data));
      });
  };

  return (
    <div className="app-wrapper">
      <header className="header">
        <h1>FAST-FOOD MONOLITH 🍔</h1>
        <span>Hotline: 0900-100-XXX</span>
      </header>

      <div className="container">
        {/* MENU */}
        <section className="menu-section">
          <h2>Thực Đơn</h2>
          <div className="food-grid">
            {foods.map(food => (
              <div key={food.id} className="food-card">
                <div>
                  <div className="food-name">{food.name}</div>
                  <div className="food-price">{food.price.toLocaleString()}đ</div>
                </div>
                <button className="btn-add" onClick={() => addToCart(food)}>+ Thêm</button>
              </div>
            ))}
          </div>
        </section>

        {/* GIỎ HÀNG */}
        <section className="cart-section">
          <h2>Giỏ Hàng</h2>
          <input 
            className="input-name" 
            placeholder="Tên của bạn..." 
            value={customerName} 
            onChange={e => setCustomerName(e.target.value)} 
          />
          <div className="cart-list">
            {cart.map((item, index) => (
              <div key={index} className="cart-item">
                <span>{item.name}</span>
                <span>
                  {item.price.toLocaleString()}đ 
                  <button className="btn-remove" onClick={() => removeFromCart(index)}> ✕</button>
                </span>
              </div>
            ))}
          </div>
          <div className="total">
            <strong>Tổng cộng: {cart.reduce((s, i) => s + i.price, 0).toLocaleString()}đ</strong>
          </div>
          <button className="btn-order" onClick={placeOrder} disabled={cart.length === 0}>
            ĐẶT MÓN NGAY
          </button>
        </section>

        {/* LỊCH SỬ ĐƠN HÀNG */}
        <section className="history-section">
          <h2>Lịch Sử Đơn Hàng</h2>
          <table>
            <thead>
              <tr>
                <th>Mã Đơn</th>
                <th>Khách Hàng</th>
                <th>Chi Tiết</th>
                <th>Tổng Tiền</th>
              </tr>
            </thead>
            <tbody>
              {orders.map(order => (
                <tr key={order.id}>
                  <td>#ORD-{order.id}</td>
                  <td>{order.customerName}</td>
                  <td>{order.items.map(i => i.name).join(", ")}</td>
                  <td>{order.totalAmount.toLocaleString()}đ</td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </div>
    </div>
  );
}

export default App;