import React, { useEffect, useState } from "react";
import axios from "axios";
import './PluginDashboard.css'

const PluginDashboard = () => {
	const [plugins, setPlugins] = useState([]);

	// Fetch the list of connected plugins periodically
	useEffect(() => {
		const fetchPlugins = () => {
			axios
				.get("http://localhost:8080/api/plugins/connected")
				.then((response) => setPlugins(response.data))
				.catch((error) => console.error(error));
		};

		fetchPlugins();
		const interval = setInterval(fetchPlugins, 5000); // Update every 5 seconds

		return () => clearInterval(interval); // Clean up the interval on component unmount
	}, []);

	return (
		<div className="plugin-container">
			<h1>Connected Plugins</h1>
			<ul>
				{plugins.map((plugin, index) => (
					<li key={index}>{plugin}</li>
				))}
			</ul>
		</div>
	);
};

export default PluginDashboard;
