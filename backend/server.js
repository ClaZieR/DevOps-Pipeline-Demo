// BackEnd/server.js
const express = require('express');
const cors = require('cors');

const app = express();
const PORT = 4000;

// Enable CORS so frontend can talk to backend
app.use(cors());
app.use(express.json());

// Simple API routes
app.get('/', (req, res) => {
  res.json({ message: 'Backend is working!' });
});

app.get('/api/hello', (req, res) => {
  res.json({ 
    message: 'Hello from the backend!',
    timestamp: new Date().toISOString()
  });
});

app.get('/api/users', (req, res) => {
  res.json([
    { id: 1, name: 'John Doe', email: 'john@example.com' },
    { id: 2, name: 'Jane Smith', email: 'jane@example.com' }
  ]);
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});

