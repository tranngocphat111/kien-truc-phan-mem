import { useState, useEffect } from 'react';
import { menuService } from '../services/menuService';

export default function MenuList({ onAddToCart }) {
  const [menuItems, setMenuItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadMenu();
  }, []);

  const loadMenu = async () => {
    try {
      const data = await menuService.getAll();
      setMenuItems(data);
    } catch (err) {
      console.error('Error loading menu:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Đang tải menu...</p>;

  return (
    <div className="menu-list">
      <h2>Thực đơn</h2>
      <div className="menu-grid">
        {menuItems.map((item) => (
          <div key={item.id} className="menu-item">
            <h3>{item.name}</h3>
            <p>{item.description}</p>
            <p className="price">{item.price?.toLocaleString()} VNĐ</p>
            <p className="category">{item.category}</p>
            {onAddToCart && (
              <button onClick={() => onAddToCart(item)}>Thêm vào giỏ</button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
