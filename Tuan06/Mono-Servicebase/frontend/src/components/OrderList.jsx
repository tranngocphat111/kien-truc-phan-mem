import { useState, useEffect } from 'react';
import { orderService } from '../services/orderService';

export default function OrderList({ user }) {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user) {
      loadOrders();
    }
  }, [user]);

  const loadOrders = async () => {
    try {
      const data = await orderService.getByUserId(user.id);
      setOrders(data);
    } catch (err) {
      console.error('Error loading orders:', err);
    } finally {
      setLoading(false);
    }
  };

  if (!user) return <p>Vui lòng đăng nhập để xem đơn hàng</p>;
  if (loading) return <p>Đang tải...</p>;

  return (
    <div className="order-list">
      <h2>Đơn hàng của tôi</h2>
      {orders.length === 0 ? (
        <p>Chưa có đơn hàng nào</p>
      ) : (
        <div className="orders">
          {orders.map((order) => (
            <div key={order.id} className="order-item">
              <h3>Đơn #{order.id}</h3>
              <p>Trạng thái: <span className={`status ${order.status}`}>{order.status}</span></p>
              <p>Tổng: {order.totalAmount?.toLocaleString()} VNĐ</p>
              <p>Địa chỉ: {order.deliveryAddress}</p>
              <p>Ghi chú: {order.note || 'Không có'}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
