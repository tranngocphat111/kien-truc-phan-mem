import { useState, useEffect } from 'react';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/horizontal';

/**
 * HORIZONTAL PARTITION DEMO
 * Chia dữ liệu theo ROW (giới tính)
 * - Nam -> user_male
 * - Nữ -> user_female
 */
function HorizontalPartition() {
  const [users, setUsers] = useState([]);
  const [maleUsers, setMaleUsers] = useState([]);
  const [femaleUsers, setFemaleUsers] = useState([]);
  const [form, setForm] = useState({ name: '', email: '', age: '', gender: 'MALE' });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    setLoading(true);
    try {
      const [allRes, maleRes, femaleRes] = await Promise.all([
        axios.get(`${API_URL}/users`),
        axios.get(`${API_URL}/users/male`),
        axios.get(`${API_URL}/users/female`)
      ]);
      setUsers(allRes.data);
      setMaleUsers(maleRes.data);
      setFemaleUsers(femaleRes.data);
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
        age: parseInt(form.age)
      });
      setForm({ name: '', email: '', age: '', gender: 'MALE' });
      loadUsers();
    } catch (error) {
      alert('Error creating user: ' + error.message);
    }
  };

  const handleDelete = async (gender, id) => {
    if (window.confirm('Bạn có chắc muốn xóa?')) {
      try {
        await axios.delete(`${API_URL}/users/${gender}/${id}`);
        loadUsers();
      } catch (error) {
        alert('Error deleting user');
      }
    }
  };

  return (
    <div className="partition-section">
      <h2>🔀 Horizontal Partition (Chia theo ROW)</h2>

      <div className="explanation">
        <p><strong>Nguyên lý:</strong> Dữ liệu được chia vào các bảng khác nhau dựa trên điều kiện (giới tính)</p>
        <ul>
          <li>👨 <code>user_male</code> - Chứa tất cả user NAM</li>
          <li>👩 <code>user_female</code> - Chứa tất cả user NỮ</li>
        </ul>
        <p><strong>Ưu điểm:</strong> Query nhanh hơn, dễ scale, giảm kích thước bảng</p>
      </div>

      {/* Form thêm user */}
      <form onSubmit={handleSubmit} className="form">
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
        <input
          type="number"
          placeholder="Tuổi"
          value={form.age}
          onChange={(e) => setForm({ ...form, age: e.target.value })}
          required
        />
        <select
          value={form.gender}
          onChange={(e) => setForm({ ...form, gender: e.target.value })}
        >
          <option value="MALE">Nam (→ user_male)</option>
          <option value="FEMALE">Nữ (→ user_female)</option>
        </select>
        <button type="submit">Thêm User</button>
      </form>

      {loading && <p>Loading...</p>}

      {/* Hiển thị 2 bảng riêng biệt */}
      <div className="tables-container">
        <div className="table-wrapper">
          <h3>👨 Bảng user_male ({maleUsers.length} records)</h3>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Email</th>
                <th>Tuổi</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {maleUsers.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.age}</td>
                  <td>
                    <button onClick={() => handleDelete('MALE', user.id)} className="btn-delete">
                      Xóa
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="table-wrapper">
          <h3>👩 Bảng user_female ({femaleUsers.length} records)</h3>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Email</th>
                <th>Tuổi</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {femaleUsers.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.age}</td>
                  <td>
                    <button onClick={() => handleDelete('FEMALE', user.id)} className="btn-delete">
                      Xóa
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default HorizontalPartition;
