// FrontEnd/src/App.js
import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [message, setMessage] = useState('');
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const API_URL = process.env.REACT_APP_API_URL;

  // Test backend connection
  const fetchHello = async () => {
    setLoading(true);
    try {
      const response = await fetch(`${API_URL}/api/hello`);
      const data = await response.json();
      setMessage(data.message);
    } catch (error) {
      setMessage('Failed to connect to backend');
      console.error('Error:', error);
    }
    setLoading(false);
  };

  // Fetch users from backend
  const fetchUsers = async () => {
    try {
      const response = await fetch(`${API_URL}/api/users`);
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>React + Node Docker Test</h1>
        
        <div className="test-section">
          <button onClick={fetchHello} disabled={loading}>
            {loading ? 'Loading...' : 'Test Backend Connection'}
          </button>
          {message && <p className="message">{message}</p>}
        </div>

        <div className="users-section">
          <h2>Users from Backend:</h2>
          {users.length > 0 ? (
            <ul>
              {users.map(user => (
                <li key={user.id}>
                  {user.name} - {user.email}
                </li>
              ))}
            </ul>
          ) : (
            <p>No users found</p>
          )}
        </div>
      </header>
    </div>
  );
}

export default App;
