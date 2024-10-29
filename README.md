# Microkernel Architecture Demo

This project demonstrates the concepts of microkernel architecture through a practical example. It showcases a core kernel, a React frontend application, and several plugins that communicate and interact.

## Project Overview

The project follows a microkernel design pattern, where the core kernel manages essential functionalities, including:

* **Simple Authentication System:** Provides basic user authentication with credentials "user" and "admin."
* **Plugin Registry:** Keeps track of connected plugins through heartbeat messages.
* **Endpoint Exposure:** Exposes APIs for plugins to connect and provide information.

The React frontend application displays:

* **Authentication:** Provides core kernel compressed into the most vital functionalities of the project, that being a simple local auth system.
* **Dashboard:** Shows a list of connected plugins, allowing real-time monitoring.

Plugins are self-contained units implementing specific functionalities. They communicate with the core kernel using a heartbeat mechanism:

* **Sending Heartbeats:** Plugins periodically send heartbeat messages to the core.
* **Status Monitoring:** The core monitors plugin activity and updates the dashboard accordingly.

## Key Features

* **Modular Design:** Clear separation between core, frontend, and plugins promotes maintainability and extensibility.
* **Real-time Monitoring:** Provides immediate updates on plugin status within the React application.
* **Resilient Communication:** Plugins handle failures by resending heartbeats if the core is unavailable.
* **Spring Boot Integration:** Leverages Spring Boot for streamlined application development and deployment.

## How to Run the Project

**Prerequisites:**

* Node.js and npm (or yarn) installed
* Java 17

**Instructions:**

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/arizaxel16/Microkernel-Architecture-Demo.git
   ```

2. **Navigate to Directories:**
   ```bash
   cd Microkernel-Architecture-Demo
   ```

3. **Run the React App:** (Inside `frontend` directory)
   ```bash
   npm install
   npm start
   ```

4. **Start the Core:** (Inside `core` directory)
   ```bash
   ./gradlew bootRun
   ```

5. **Start the Plugins:** (Inside each plugin directory, e.g., `plugin-1`)
   ```bash
   ./gradlew bootRun
   ```

**Expected Behavior:**

1. Open your browser at `http://localhost:3000`.
2. You should see a login form. Enter "user" for username and "admin" for password.
3. Upon successful login, the dashboard will display a list of connected plugins.
4. If a plugin fails to connect or disconnects, it will be removed from the list.

**Customization:**

* You can add more plugins following the template provided (`plugin-template`).
* Modify plugin properties like `core.url` and `plugin.id` within each plugin's `application.properties` file.

**## Running the Project on a Development Server**

To run the project on a development server, adjust the port configurations:

* **Core:** Update the port in `application.properties` (e.g., `server.port=8080`).
* **Frontend:** Modify the port in `package.json` (`"start": "react-scripts start --port 3000"`).
* **Plugins:** Adjust the port in each plugin's `application.properties`.

Start the components using their respective commands, and access the frontend application at the updated URL (e.g., `http://localhost:3000`).

**## Project Structure**

```
microkernel-demo/
├── core/  # Core kernel application (Spring Boot)
│   ├── pom.xml
│   └── src/main/java/...  # Core kernel source code
├── frontend/  # React frontend application
│   ├── package.json
│   └── src/...  # React frontend source code
├── plugin-1/  # Example plugin 1 (Spring Boot)
│   ├── pom.xml
│   └── src/main/java/...  # Plugin 1 source code
├── plugin-2/  # Example plugin 2 (Spring Boot)
│   ... (similar structure as plugin-1)
├── ... (more plugin directories)
└── README.md  # This file
```
