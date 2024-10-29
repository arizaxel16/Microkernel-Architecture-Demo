import React from 'react';
import PluginDashboard from '../../components/PluginDashboard'
import './Dashboard.css'

const Dashboard = () => {
    return (
        <div className='dashboard-container'>
            <h1>Plugin Dashboard</h1>
            <PluginDashboard />
        </div>
    );
};

export default Dashboard;
