import React from 'react';
import PluginDashboard from '../components/PluginDashboard'

const Dashboard = () => {
    return (
        <div>
            <h2>Dashboard</h2>
            <p>Welcome to the dashboard!</p>
            <h3>Plugins:</h3>
            <PluginDashboard />
        </div>
    );
};

export default Dashboard;
