import React from 'react';
import PluginDashboard from '../../components/PluginDashboard'
import './Dashboard.css'

const Dashboard = () => {
    return (
        <div className='dashboard-container'>
            <h1>Welcome to the dashboard!</h1>
            <h3>Plugins:</h3>
            <PluginDashboard />
        </div>
    );
};

export default Dashboard;
