import { useState } from 'react';
import HorizontalPartition from './components/HorizontalPartition';
import VerticalPartition from './components/VerticalPartition';
import FunctionPartition from './components/FunctionPartition';
import './App.css';

/**
 * DATABASE PARTITION DEMO
 * Demo 3 loại partition trong MariaDB với Spring Boot
 */
function App() {
  const [activeTab, setActiveTab] = useState('horizontal');

  return (
    <div className="app">
      <header>
        <h1>🗄️ Database Partition Demo</h1>
        <p>Demo 3 loại Database Partition với MariaDB + Spring Boot</p>
      </header>

      {/* Tab Navigation */}
      <nav className="tabs">
        <button
          className={activeTab === 'horizontal' ? 'active' : ''}
          onClick={() => setActiveTab('horizontal')}
        >
          🔀 Horizontal (Row)
        </button>
        <button
          className={activeTab === 'vertical' ? 'active' : ''}
          onClick={() => setActiveTab('vertical')}
        >
          📊 Vertical (Column)
        </button>
        <button
          className={activeTab === 'function' ? 'active' : ''}
          onClick={() => setActiveTab('function')}
        >
          ⚙️ Function
        </button>
      </nav>

      {/* Content */}
      <main>
        {activeTab === 'horizontal' && <HorizontalPartition />}
        {activeTab === 'vertical' && <VerticalPartition />}
        {activeTab === 'function' && <FunctionPartition />}
      </main>

      <footer>
        <p>IUH - Kiến trúc phần mềm - Tuần 06</p>
      </footer>
    </div>
  );
}

export default App;
