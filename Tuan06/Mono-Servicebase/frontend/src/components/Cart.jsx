import { useState } from 'react';
import { orderService } from '../services/orderService';

export default function Cart({ cart, user, onClearCart }) {
  const [note, setNote] = useState('');
  const [message, setMessage] = useState('');

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const handleOrder = async () => {
    if (!user) {
      setMessage('Vui lòng đăng nhập để đặt hàng!');
      return;
    }

    const order = {
      userId: user.id,
      totalAmount: total,
      deliveryAddress: user.address,
      note: note,
      items: cart.map((item) => ({
        menuItemId: item.id,
        quantity: item.quantity,
        price: item.price
      }))
    };

    try {
      await orderService.create(order);
      setMessage('Đặt hàng thành công!');
      onClearCart();
    } catch (err) {
      setMessage('Lỗi đặt hàng!');
    }
  };

  return (
    <div className="cart">
      <h2>Giỏ hàng</h2>
      {message && <p className="message">{message}</p>}
      {cart.length === 0 ? (
        <p>Giỏ hàng trống</p>
      ) : (
        <>
          <ul>
            {cart.map((item, index) => (
              <li key={index}>
                {item.name} x {item.quantity} = {(item.price * item.quantity).toLocaleString()} VNĐ
              </li>
            ))}
          </ul>
          <p className="total">Tổng: {total.toLocaleString()} VNĐ</p>
          <textarea
            placeholder="Ghi chú đơn hàng..."
            value={note}
            onChange={(e) => setNote(e.target.value)}
          />
          <button onClick={handleOrder}>Đặt hàng</button>
        </>
      )}
    </div>
  );
}
