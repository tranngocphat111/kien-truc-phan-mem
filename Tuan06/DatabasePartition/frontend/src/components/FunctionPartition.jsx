import { useState, useEffect } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/function';

/**
 * FUNCTION PARTITION DEMO
 * Chia dữ liệu theo CHỨC NĂNG
 * - user_order: Quản lý đơn hàng
 * - user_log: Ghi log hoạt động
 */
function FunctionPartition() {
  const [orders, setOrders] = useState([]);
  const [logs, setLogs] = useState([]);
  const [orderForm, setOrderForm] = useState({ userId: '', productName: '', amount: '' });
  const [logForm, setLogForm] = useState({ userId: '', action: 'LOGIN', description: '', ipAddress: '' });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [ordersRes, logsRes] = await Promise.all([
        axios.get(`${API_URL}/orders`),
        axios.get(`${API_URL}/logs`)
      ]);
      setOrders(ordersRes.data);
      setLogs(logsRes.data);
    } catch (error) {
      console.error('Error loading data:', error);
    }
    setLoading(false);
  };

  const handleCreateOrder = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`${API_URL}/orders`, {
        ...orderForm,
        userId: parseInt(orderForm.userId),
        amount: parseFloat(orderForm.amount)
      });
      setOrderForm({ userId: '', productName: '', amount: '' });
      loadData();
    } catch (error) {
      alert('Error creating order: ' + error.message);
    }
  };

  const handleCreateLog = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`${API_URL}/logs`, {
        ...logForm,
        userId: parseInt(logForm.userId)
      });
      setLogForm({ userId: '', action: 'LOGIN', description: '', ipAddress: '' });
      loadData();
    } catch (error) {
      alert('Error creating log: ' + error.message);
    }
  };

  const handleUpdateStatus = async (orderId, status) => {
    try {
      await axios.put(`${API_URL}/orders/${orderId}/status?status=${status}`);
      loadData();
    } catch (error) {
      alert('Error updating order status');
    }
  };

  const formatDate = (dateStr) => {
    if (!dateStr) return 'N/A';
    return new Date(dateStr).toLocaleString('vi-VN');
  };

  return (
    <div className="partition-section">
      <h2>⚙️ Function Partition (Chia theo CHỨC NĂNG)</h2>

      <div className="explanation">
        <p><strong>Nguyên lý:</strong> Chia dữ liệu theo chức năng nghiệp vụ</p>
        <ul>
          <li>🛒 <code>user_order</code> - Quản lý đơn hàng</li>
          <li>📋 <code>user_log</code> - Ghi log hoạt động</li>
        </ul>
        <p><strong>Ưu điểm:</strong> Mỗi bảng phục vụ một chức năng riêng, dễ bảo trì, có thể đặt trên các server khác nhau</p>
      </div>

      {loading && <p>Loading...</p>}

      <div className="tables-container">
        {/* Orders Section */}
        <div className="table-wrapper">
          <h3>🛒 Bảng user_order ({orders.length} records)</h3>

          <form onSubmit={handleCreateOrder} className="form mini-form">
            <input
              type="number"
              placeholder="User ID"
              value={orderForm.userId}
              onChange={(e) => setOrderForm({ ...orderForm, userId: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Tên sản phẩm"
              value={orderForm.productName}
              onChange={(e) => setOrderForm({ ...orderForm, productName: e.target.value })}
              required
            />
            <input
              type="number"
              placeholder="Số tiền"
              value={orderForm.amount}
              onChange={(e) => setOrderForm({ ...orderForm, amount: e.target.value })}
              required
            />
            <button type="submit">Tạo đơn</button>
          </form>

          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Sản phẩm</th>
                <th>Số tiền</th>
                <th>Trạng thái</th>
                <th>Ngày tạo</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((order) => (
                <tr key={order.id}>
                  <td>{order.id}</td>
                  <td>{order.userId}</td>
                  <td>{order.productName}</td>
                  <td>{order.amount?.toLocaleString('vi-VN')} VND</td>
                  <td>
                    <span className={`status status-${order.status?.toLowerCase()}`}>
                      {order.status}
                    </span>
                  </td>
                  <td>{formatDate(order.orderDate)}</td>
                  <td>
                    <select
                      value={order.status}
                      onChange={(e) => handleUpdateStatus(order.id, e.target.value)}
                    >
                      <option value="PENDING">PENDING</option>
                      <option value="CONFIRMED">CONFIRMED</option>
                      <option value="SHIPPED">SHIPPED</option>
                      <option value="DELIVERED">DELIVERED</option>
                      <option value="CANCELLED">CANCELLED</option>
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Logs Section */}
        <div className="table-wrapper">
          <h3>📋 Bảng user_log ({logs.length} records)</h3>

          <form onSubmit={handleCreateLog} className="form mini-form">
            <input
              type="number"
              placeholder="User ID"
              value={logForm.userId}
              onChange={(e) => setLogForm({ ...logForm, userId: e.target.value })}
              required
            />
            <select
              value={logForm.action}
              onChange={(e) => setLogForm({ ...logForm, action: e.target.value })}
            >
              <option value="LOGIN">LOGIN</option>
              <option value="LOGOUT">LOGOUT</option>
              <option value="VIEW">VIEW</option>
              <option value="PURCHASE">PURCHASE</option>
            </select>
            <input
              type="text"
              placeholder="Mô tả"
              value={logForm.description}
              onChange={(e) => setLogForm({ ...logForm, description: e.target.value })}
            />
            <input
              type="text"
              placeholder="IP Address"
              value={logForm.ipAddress}
              onChange={(e) => setLogForm({ ...logForm, ipAddress: e.target.value })}
            />
            <button type="submit">Ghi log</button>
          </form>

          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Action</th>
                <th>Mô tả</th>
                <th>IP</th>
                <th>Thời gian</th>
              </tr>
            </thead>
            <tbody>
              {logs.map((log) => (
                <tr key={log.id}>
                  <td>{log.id}</td>
                  <td>{log.userId}</td>
                  <td>
                    <span className={`action action-${log.action?.toLowerCase()}`}>
                      {log.action}
                    </span>
                  </td>
                  <td>{log.description || 'N/A'}</td>
                  <td>{log.ipAddress || 'N/A'}</td>
                  <td>{formatDate(log.logTime)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default FunctionPartition;
