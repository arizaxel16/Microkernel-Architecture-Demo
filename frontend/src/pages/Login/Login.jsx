import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Login.css'

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/login', { username, password });
            
            // Check the response body for success message
            if (response.status === 200) {
                navigate('/dashboard'); // Navigate to the dashboard on successful login
            } else {
                setError(response.data); // Set error message from response
            }
        } catch (err) {
            // Handle error, including network errors
            if (err.response) {
                setError(err.response.data); // Set error message from response body
            } else {
                setError('An error occurred. Please try again.');
            }
        }
    };

    return (
        <div className="page">
            <div className="container">
                <form onSubmit={handleSubmit}>
                    <div className="brand-title">LOGIN</div>
                    <div className="inputs">
                        <label>USER</label>
                        <input 
                                type="text" 
                                placeholder="Username" 
                                value={username} 
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        <label>PASSWORD</label>
                        <input 
                                type="password" 
                                placeholder="Password" 
                                value={password} 
                                onChange={(e) => setPassword(e.target.value)} 
                                required
                            />
                        <button type="submit">Login</button>
                        {error && <p style={{ color: 'red' }}>{error}</p>}
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;
