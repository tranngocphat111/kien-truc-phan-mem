import { useState } from 'react';
import { userService } from '../services/userService';

export default function Register({ onRegister }) {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
    fullName: '',
    phone: '',
    address: ''
  });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await userService.register(formData);
      setMessage('Đăng ký thành công!');
      onRegister && onRegister();
    } catch (err) {
      setMessage('Lỗi đăng ký!');
    }
  };

  return (
    <div className="form-container">
      <h2>Đăng ký</h2>
      {message && <p className="message">{message}</p>}
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" onChange={handleChange} required />
        <input name="email" type="email" placeholder="Email" onChange={handleChange} required />
        <input name="fullName" placeholder="Họ tên" onChange={handleChange} />
        <input name="phone" placeholder="Số điện thoại" onChange={handleChange} />
        <input name="address" placeholder="Địa chỉ" onChange={handleChange} />
        <button type="submit">Đăng ký</button>
      </form>
    </div>
  );
}
