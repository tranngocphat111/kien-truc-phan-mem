import { useState } from 'react';
import Login from './components/Login';
import Register from './components/Register';
import MenuList from './components/MenuList';
import Cart from './components/Cart';
import OrderList from './components/OrderList';
import './App.css';

function App() {
  const [user, setUser] = useState(null);
  const [cart, setCart] = useState([]);
  const [page, setPage] = useState('menu');

  const handleLogin = (userData) => {
    setUser(userData);
    setPage('menu');
  };

  const handleLogout = () => {
    setUser(null);
    setCart([]);
    setPage('menu');
  };

  const addToCart = (item) => {
    const existing = cart.find((c) => c.id === item.id);
    if (existing) {
      setCart(cart.map((c) => c.id === item.id ? { ...c, quantity: c.quantity + 1 } : c));
    } else {
      setCart([...cart, { ...item, quantity: 1 }]);
    }
  };

  const clearCart = () => setCart([]);

  return (
    <div className="app">
      <header>
        <h1>Food Delivery</h1>
        <nav>
          <button onClick={() => setPage('menu')}>Thuc don</button>
          <button onClick={() => setPage('cart')}>Gio hang ({cart.length})</button>
          {user ? (
            <>
              <button onClick={() => setPage('orders')}>Don hang</button>
              <span>Xin chao, {user.fullName || user.username}</span>
              <button onClick={handleLogout}>Dang xuat</button>
            </>
          ) : (
            <>
              <button onClick={() => setPage('login')}>Dang nhap</button>
              <button onClick={() => setPage('register')}>Dang ky</button>
            </>
          )}
        </nav>
      </header>

      <main>
        {page === 'login' && <Login onLogin={handleLogin} />}
        {page === 'register' && <Register onRegister={() => setPage('login')} />}
        {page === 'menu' && <MenuList onAddToCart={addToCart} />}
        {page === 'cart' && <Cart cart={cart} user={user} onClearCart={clearCart} />}
        {page === 'orders' && <OrderList user={user} />}
      </main>

      <footer>
        <p>Online Food Delivery - Service-Based Architecture</p>
      </footer>
    </div>
  );
}

export default App;
