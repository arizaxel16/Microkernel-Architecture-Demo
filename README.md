# Microkernel Architecture Demo

## Overview

This project demonstrates the implementation of a **Microkernel Architecture** using **Spring Boot** multi-module projects. The kernel serves as a minimal core system that provides fundamental services and capabilities, while plugins offer additional functionalities that extend and enhance the system without altering the core. This design pattern allows for a modular, scalable, and flexible architecture that can easily be expanded by adding or removing plugins.

In this demo, the microkernel is implemented through a Spring Boot-based kernel project that manages various plugins and their interactions. The kernel provides the core logic, including authentication, while the plugins provide specialized functionalities like logging and metrics. The system uses a React frontend for visualizing and interacting with the plugins.

## Key Concepts

### Microkernel Architecture

The **Microkernel Architecture**, also known as the plug-in architecture, is a design pattern where the core system (the microkernel) provides only the essential functionalities required for the system to run. Plugins are then added to extend the capabilities of the system. The microkernel manages the lifecycle of these plugins, ensuring that they can be dynamically loaded, started, and stopped without affecting the core functionality.

Key characteristics of the Microkernel Architecture include:
- **Core system (Microkernel)**: Handles basic functions such as authentication, system initialization, and communication with plugins.
- **Plugins**: Independent components that can be added or removed to extend the functionality of the core system.
- **Isolation**: Plugins operate independently of each other and the core system, preventing issues in one plugin from affecting the rest of the system.
- **Dynamic Loading**: Plugins are loaded and unloaded at runtime, allowing for flexibility in extending the system.

### Spring Boot Multi-Module Project

This project is organized as a **Spring Boot multi-module** setup. The architecture is divided into several modules:
1. **Kernel Module**: Contains the core logic and the contract/interface for plugins.
2. **Plugin Modules**: Independent modules, each representing a plugin that implements the plugin contract defined in the kernel module.
3. **Frontend Module**: A simple React application that communicates with the backend via Axios to visualize and interact with the plugins.

### Plugin Contract Interface

The **plugin contract** is defined in the `com.core.plugin` package, specifically in the `Plugin` interface. All plugins must implement this interface, which ensures consistent behavior and interaction with the core system.

```java
package com.core.plugin;

public interface Plugin {
    String getName();
    void init();
    void start();
    void stop();
    String getVersion();
    boolean isEnabled();
}
```

The methods in this interface allow the kernel to:
- Retrieve the name and version of the plugin.
- Initialize, start, and stop the plugin.
- Check whether the plugin is enabled or not.

### Plugins in the Kernel

The **kernel module** holds all the controllers, services, and data transfer objects (DTOs) for the core logic. It also manages the loading, starting, and stopping of plugins via the `Plugin` interface. The kernel ensures that the plugins are properly initialized and can be dynamically loaded.

Within the `plugins` folder of the kernel project, there are `.jar` files representing each plugin. These plugins can be loaded and unloaded at runtime. Currently, there are two preloaded plugins in the project:
1. **Logging Plugin**: A simple plugin template for logging functionality.
2. **Metrics Plugin**: A basic plugin template for metrics collection.

Both plugins implement the `Plugin` interface and can be customized to fit specific use cases.

### Plugin Structure

Each plugin is a separate Spring Boot module with its own Gradle configuration. The structure of each plugin follows the same pattern, ensuring consistency across the system. The plugin modules contain the implementation of the `Plugin` interface and any additional logic needed for the plugin’s functionality.

### Frontend

The **frontend module** is a simple **React** application that serves as a user interface to interact with the backend. It communicates with the Spring Boot backend using **Axios** to make HTTP requests to the server. The frontend allows users to visualize the status of the plugins and interact with them.

### Authentication and Core Logic

The **kernel module** contains the core authentication logic, which is fundamental to the operation of the system. The kernel is responsible for managing user authentication, session handling, and basic system operations. The plugins extend this functionality by adding specialized features on top of the core authentication, such as logging and metrics.

## How It Works

1. **Kernel Initialization**: The kernel module starts by loading the core system components, including authentication and basic services.
2. **Plugin Loading**: The kernel dynamically loads the available plugins from the `plugins` folder and initializes them according to the contract defined in the `Plugin` interface.
3. **Plugin Interaction**: The frontend application communicates with the backend to interact with the plugins, visualize their status, and enable/disable them as needed.
4. **Plugin Management**: The kernel manages the lifecycle of each plugin, allowing them to be started, stopped, and reloaded without disrupting the core functionality.

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 7.x or higher
- Node.js and npm (for the frontend)

### Clone the Repository

```bash
git clone https://github.com/arizaxel16/Microkernel-Architecture-Demo.git
cd Microkernel-Architecture-Demo
```

### Build the Project

To build the entire project, including the backend and frontend modules, run the following commands:

#### Backend (Spring Boot)

```bash
cd backend
./gradlew build
./gradlew bootRun
```

This will compile and start the Spring Boot backend application.

#### Frontend (React)

```bash
cd frontend
npm install
npm start
```

This will start the React development server. The frontend will be available at `http://localhost:3000`.

### Access the System

Once both the backend and frontend are running, you can access the system by navigating to `http://localhost:3000`. The React frontend will allow you to interact with the available plugins, visualize their status, and trigger actions like enabling/disabling the plugins.

### Customizing Plugins

To add or modify a plugin:
1. Create a new module under the `plugin` folder.
2. Implement the `Plugin` interface in your plugin module.
3. Add any specific logic needed for your plugin's functionality.
4. Rebuild the project and restart the system to load the new plugin.

## How to Create Your Own Plugin

Creating your own plugin for this microkernel architecture is easy. Follow the steps below to copy an existing plugin template, customize it with your own methods, and integrate it into the kernel.

### Step-by-Step Guide to Create a Custom Plugin

1. **Navigate to the Plugin Directory**  
   In the project structure, navigate to the `plugin` folder where the existing plugins (e.g., `logging-plugin` and `metrics-plugin`) are located. Each plugin has its own folder with a Gradle build file.

2. **Copy an Existing Plugin Template**  
   To get started quickly, copy either the `logging-plugin` or `metrics-plugin` folder and give it a meaningful name for your new plugin. For example, let's call it `my-custom-plugin`.

   ```bash
     cp -r plugins/logging-plugin plugins/my-custom-plugin
   ```

3. **Rename Files and Adjust Plugin Class**  
   Inside the newly copied plugin folder, change the class names and references to reflect the name of your new plugin. For example:
   - Rename `LoggingPlugin.java` to `MyCustomPlugin.java`.
   - Update the class name inside the Java file to `MyCustomPlugin`.
   
   You will also need to modify the `Plugin` interface methods (such as `getName()`, `init()`, `start()`, etc.) to reflect the specific behavior of your new plugin.

   Here’s an example of what the new plugin implementation might look like:

   ```java
   package com.plugin.mycustom;

   import com.core.plugin.Plugin;

   public class MyCustomPlugin implements Plugin {
       private boolean enabled = false;

       @Override
       public String getName() {
           return "My Custom Plugin";
       }

       @Override
       public void init() {
           System.out.println("Initializing My Custom Plugin...");
       }

       @Override
       public void start() {
           enabled = true;
           System.out.println("Starting My Custom Plugin...");
       }

       @Override
       public void stop() {
           enabled = false;
           System.out.println("Stopping My Custom Plugin...");
       }

       @Override
       public String getVersion() {
           return "1.0.0";
       }

       @Override
       public boolean isEnabled() {
           return enabled;
       }

       // Custom method specific to this plugin
       public void customMethod() {
           System.out.println("Executing custom method...");
       }
   }
   ```

4. **Add Your Plugin-Specific Logic**  
   Now that the basic plugin structure is in place, you can add your own custom methods and business logic. For example, you might want to add a method that logs specific data, interacts with an external API, or performs a unique task relevant to your use case.

5. **Update `build.gradle` (if needed)**  
   In most cases, you won't need to modify the `build.gradle` file if you copied an existing template. However, if your plugin introduces additional dependencies or configurations, you can add them to the `dependencies` block in the `build.gradle` file for your plugin.

6. **Build the Plugin**  
   Once you’ve implemented your custom plugin, you’ll need to build the plugin’s `.jar` file. Navigate to the plugin directory in your terminal and run the Gradle build command:

   ```bash
   cd plugins/my-custom-plugin
   ./gradlew build
   ```

   This will generate a `.jar` file for your plugin located in the `build/libs/` directory.

7. **Move the Plugin JAR to the Kernel**  
   After the plugin is built, move the generated `.jar` file into the `plugins` folder of the **kernel** module, where all other plugin JARs are stored. This allows the kernel to load and interact with the new plugin.

   ```bash
   mv build/libs/my-custom-plugin-1.0.0.jar /path/to/plugins/jar/in/kernel
   ```

8. **Restart the Application**  
   With your new plugin now in the kernel’s `plugins` directory, restart the Spring Boot application to load the plugin into the system. You can do this by stopping the current backend application and running:

   ```bash
   ./gradlew bootRun
   ```

9. **Verify the Plugin in the Frontend**  
   Finally, navigate to the React frontend at `http://localhost:3000` and verify that your new plugin appears in the plugin list. You should be able to see the plugin name, version, and status (whether it's enabled or disabled). You can also interact with it using any custom methods you added.

   If you included any custom methods in your plugin, ensure that they are visible and interactable via the frontend interface as required.

---

### Example Plugin Customization

To make it easier to customize, let's walk through an example where you add a custom method to your plugin that prints a message to the console.

1. In the `MyCustomPlugin.java` file, we added a method called `customMethod`:

   ```java
   public void customMethod() {
       System.out.println("Executing custom method...");
   }
   ```

2. In the frontend React application, you can add a UI element (like a button) to trigger this method and display the result to the user. For example, you might use Axios to make a request to the backend to call this method, and display the result on the frontend. Remember to update the kernel's controller and services to interact with the plugins through the core and provide he exposed endpoint to the frontend.

### Summary

By following these steps, you can easily create your own custom plugin, modify its behavior, and integrate it with the existing microkernel architecture. This approach allows you to extend the system’s capabilities while maintaining a clean separation between core functionalities and extendable modules.

---

## Conclusion

This project demonstrates the power and flexibility of the Microkernel Architecture, using Spring Boot to implement a modular system where plugins can be added or removed dynamically. By separating core functionalities from extensible plugins, this architecture allows for a scalable and maintainable system. The frontend provides an easy-to-use interface for managing the plugins, ensuring that users can interact with the system in a straightforward manner. 

Feel free to fork the repository, experiment with the plugins, and expand the functionality to suit your needs.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
