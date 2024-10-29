import React, { useEffect, useState } from "react";
import axios from "axios";
import "./PluginDashboard.css";

const PluginDashboard = () => {
	const [plugins, setPlugins] = useState([]);

	// Fetch the list of connected plugins periodically
	useEffect(() => {
		const fetchPlugins = async () => {
			try {
				const response = await axios.get(
					"http://localhost:8080/api/plugins/connected"
				);
				setPlugins(response.data);
			} catch (error) {
				console.error(error);
			}
		};

		fetchPlugins();
		const interval = setInterval(fetchPlugins, 5000); // Update every 5 seconds

		return () => clearInterval(interval); // Clean up the interval on component unmount
	}, []);

	return (
		<div className="plugin-container">
			<div className="table-header">
				<div className="header__item">Plugin</div>
				<div className="header__item">Status</div>
			</div>

			<ul>
				{plugins.map((plugin, index) => (
					<li
						key={index}
						className={
							index % 2 === 0 ? "table-row even-row" : "table-row odd-row"
						}
					>
						<div className="table-data">{plugin.name || plugin}</div>
						<div className="table-data">
							CONNECTED <span className="green-circle"></span>
						</div>
					</li>
				))}
			</ul>
		</div>
	);
};

export default PluginDashboard;
