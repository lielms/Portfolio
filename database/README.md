<!--
Hey, thanks for using the awesome-readme-template template.  
If you have any enhancements, then fork this project and create a pull request 
or just open an issue with the label "enhancement".

Don't forget to give this project a star for additional support ;)
Maybe you can mention me or this repo in the acknowledgements too
-->
<!-- Table of Contents -->
# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
  * [Tech Stack](#space_invader-tech-stack)
  * [Features](#dart-features)
  * [Database Design](#computer-database-design)
- [Usage & Simulation](#eyes-usage)
- [Roadmap](#compass-roadmap)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)

  

<!-- About the Project -->
## :star2: About the Project

This project is an enhanced version of the final thermostat prototype from CS 350: Emerging Systems Architectures and Technologies. It was originally designed as a real-time hardware controller on a Raspberry Pi. By integrating MongoDB, I was able to implement a custom database module, the system now captures historical temperature trends, state transitions, and user-driven setpoint changes, providing a foundation for long-term data analysis and energy efficiency insights.

<!-- TechStack -->
### :space_invader: Tech Stack

<details>
  <summary>Client</summary>
  <ul>
    <li>Python</li>
   <li>MongoDB</li>
   <li>PyMongno</li>
   <li>Raspberry Pi GPIO</li>
    <li>I2C/UART</li>
    <li>Threading</li>
  </ul>
</details>

<!-- Features -->
### :dart: Features

- Asynchronous Logging: Uses a background worker thread and queue to ensure database writes never block real-time sensor polling or LCD updates.
- Fault Tolerance: Graceful degradation: if the MongoDB instance is offline, the thermostat continues to operate in "local-only" mode.
- Event Tracking: Logs specific triggers like state_change and setpoint_increase rather than just periodic snapshots.
- Aggregation Engine: Includes built-in methods for calculating min/max/avg temperatures over a 24-hour window using MongoDB pipelines.

<!-- Database Design -->
### :computer: Database Design

The system utilizes a flexible NoSQL schema to handle the continuous stream of IoT data:

```javascript
{
  "timestamp": "2026-02-14T10:30:00Z",
  "state": "heat",
  "temperature": 68.5,
  "setpoint": 72,
  "event_type": "periodic" // or "state_change", "setpoint_change"
}
```


<!-- Usage -->
## :eyes: Usage

1. Start the Simulator (Server Side)

```javascript
python3 ThermostatServer-Simulator.py
```

2. Run the Thermostat (Client Side)
   
```javascript
python3 Thermostat.py
```
<!-- Roadmap -->
## :compass: Roadmap

* [x] Integrate PyMongo for database connectivity
* [x] Implement Asynchronous Threaded Logging
* [x] Design NoSQL schema for IoT events
* [ ] Build Web Dashboard for data visualization

<!-- Contact -->
## :handshake: Contact

Liel Simon - liel.simon@snhu.edu

Portfolio: [https://lielms.github.io/](https://lielms.github.io/)


<!-- Acknowledgments -->
## :gem: Acknowledgements

 - SNHU CS 350: Emerging Systems Architectures and Technologies
 - readme template : https://github.com/Louis3797/awesome-readme-template/blob/main/README.md

