import { useState, useEffect } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/vertical';

/**
 * VERTICAL PARTITION DEMO
 * Chia dữ liệu theo COLUMN
 * - user_basic: thông tin cơ bản (truy cập thường xuyên)
 * - user_detail: thông tin chi tiết (ít truy cập)
 */
function VerticalPartition() {
  const [basicUsers, setBasicUsers] = useState([]);
  const [fullUsers, setFullUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [form, setForm] = useState({
    name: '', email: '', gender: 'MALE',
    address: '', phone: '', bio: '', age: ''
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    setLoading(true);
    try {
      const [basicRes, fullRes] = await Promise.all([
        axios.get(`${API_URL}/users/basic`),
        axios.get(`${API_URL}/users/full`)
      ]);
      setBasicUsers(basicRes.data);
      setFullUsers(fullRes.data);
    } catch (error) {
      console.error('Error loading users:', error);
    }
    setLoading(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`${API_URL}/users`, {
        ...form,
        age: parseInt(form.age) || null
      });
      setForm({ name: '', email: '', gender: 'MALE', address: '', phone: '', bio: '', age: '' });
      loadUsers();
    } catch (error) {
      alert('Error creating user: ' + error.message);
    }
  };

  const handleViewDetail = async (userId) => {
    try {
      const res = await axios.get(`${API_URL}/users/${userId}`);
      setSelectedUser(res.data);
    } catch (error) {
      alert('Error loading user detail');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Bạn có chắc muốn xóa?')) {
      try {
        await axios.delete(`${API_URL}/users/${id}`);
        setSelectedUser(null);
        loadUsers();
      } catch (error) {
        alert('Error deleting user');
      }
    }
  };

  return (
    <div className="partition-section">
      <h2>📊 Vertical Partition (Chia theo COLUMN)</h2>

      <div className="explanation">
        <p><strong>Nguyên lý:</strong> Chia các cột của bảng thành nhiều bảng riêng biệt</p>
        <ul>
          <li>📋 <code>user_basic</code> - Thông tin cơ bản: id, name, email (truy vấn thường xuyên)</li>
          <li>📝 <code>user_detail</code> - Thông tin chi tiết: address, phone, bio (ít truy vấn)</li>
        </ul>
        <p><strong>Ưu điểm:</strong> Query nhanh khi chỉ cần thông tin cơ bản, giảm I/O, dễ cache</p>
      </div>

      {/* Form thêm user */}
      <form onSubmit={handleSubmit} className="form vertical-form">
        <div className="form-section">
          <h4>📋 Thông tin cơ bản (→ user_basic)</h4>
          <input
            type="text"
            placeholder="Tên"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
            required
          />
          <input
            type="email"
            placeholder="Email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
            required
          />
          <select
            value={form.gender}
            onChange={(e) => setForm({ ...form, gender: e.target.value })}
          >
            <option value="MALE">Nam</option>
            <option value="FEMALE">Nữ</option>
          </select>
        </div>
        <div className="form-section">
          <h4>📝 Thông tin chi tiết (→ user_detail)</h4>
          <input
            type="text"
            placeholder="Địa chỉ"
            value={form.address}
            onChange={(e) => setForm({ ...form, address: e.target.value })}
          />
          <input
            type="text"
            placeholder="Số điện thoại"
            value={form.phone}
            onChange={(e) => setForm({ ...form, phone: e.target.value })}
          />
          <input
            type="number"
            placeholder="Tuổi"
            value={form.age}
            onChange={(e) => setForm({ ...form, age: e.target.value })}
          />
          <textarea
            placeholder="Tiểu sử"
            value={form.bio}
            onChange={(e) => setForm({ ...form, bio: e.target.value })}
          />
        </div>
        <button type="submit">Thêm User</button>
      </form>

      {loading && <p>Loading...</p>}

      {/* Hiển thị 2 bảng */}
      <div className="tables-container">
        <div className="table-wrapper">
          <h3>📋 Bảng user_basic (Query nhanh)</h3>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Email</th>
                <th>Giới tính</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {basicUsers.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.gender}</td>
                  <td>
                    <button onClick={() => handleViewDetail(user.id)} className="btn-view">
                      Xem chi tiết
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="table-wrapper">
          <h3>📝 Thông tin đầy đủ (JOIN 2 bảng)</h3>
          {selectedUser ? (
            <div className="user-detail">
              <p><strong>ID:</strong> {selectedUser.id}</p>
              <p><strong>Tên:</strong> {selectedUser.name}</p>
              <p><strong>Email:</strong> {selectedUser.email}</p>
              <p><strong>Giới tính:</strong> {selectedUser.gender}</p>
              <hr />
              <p><strong>Địa chỉ:</strong> {selectedUser.address || 'N/A'}</p>
              <p><strong>SĐT:</strong> {selectedUser.phone || 'N/A'}</p>
              <p><strong>Tuổi:</strong> {selectedUser.age || 'N/A'}</p>
              <p><strong>Tiểu sử:</strong> {selectedUser.bio || 'N/A'}</p>
              <button onClick={() => handleDelete(selectedUser.id)} className="btn-delete">
                Xóa User
              </button>
            </div>
          ) : (
            <p className="hint">👆 Click "Xem chi tiết" để load thông tin từ bảng user_detail</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default VerticalPartition;
