import React, { useEffect, useState } from "react";
import axios from "axios";
import "./PluginDashboard.css";

const PluginDashboard = () => {
    const [plugins, setPlugins] = useState([]);

    // Fetch the list of plugins periodically
    useEffect(() => {
        const fetchPlugins = async () => {
            try {
                const response = await axios.get("http://localhost:8080/plugins");
                setPlugins(response.data);
            } catch (error) {
                console.error("Error fetching plugins:", error);
            }
        };

        fetchPlugins();
        const interval = setInterval(fetchPlugins, 5000); // Update every 5 seconds

        return () => clearInterval(interval); // Cleanup interval on component unmount
    }, []);

    // Function to toggle plugin enabled/disabled state
    const togglePlugin = async (pluginName, isEnabled) => {
        try {
            const action = isEnabled ? 'disable' : 'enable';
            const response = await axios.post(`http://localhost:8080/plugins/${action}?name=${pluginName}`);
            alert(response.data); // Show the response message
            // Re-fetch plugins after toggling state
            setPlugins((prev) =>
                prev.map((plugin) =>
                    plugin.name === pluginName ? { ...plugin, enabled: !isEnabled } : plugin
                )
            );
        } catch (error) {
            console.error("Error toggling plugin:", error);
        }
    };

    return (
        <div className="plugin-container">
            <h1>Plugin Manager</h1>
            <div className="table-header">
                <div className="header__item">Plugin</div>
                <div className="header__item">Status</div>
                <div className="header__item">Action</div>
            </div>

            <ul>
                {plugins.map((plugin, index) => (
                    <li
                        key={index}
                        className={index % 2 === 0 ? "table-row even-row" : "table-row odd-row"}
                    >
                        <div className="table-data">{plugin.name}</div>
                        <div className="table-data">
                            {plugin.enabled ? (
                                <>
                                    CONNECTED <span className="green-circle"></span>
                                </>
                            ) : (
                                <>
                                    DISCONNECTED <span className="red-circle"></span>
                                </>
                            )}
                        </div>
                        <div className="table-data">
                            <button className="toggleButton" onClick={() => togglePlugin(plugin.name, plugin.enabled)}>
                                {plugin.enabled ? "Disable" : "Enable"}
                            </button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PluginDashboard;
