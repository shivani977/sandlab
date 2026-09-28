# SANDLAB

A Java-based falling sand simulation built with **Java Swing** and a 2D grid. The project simulates different materials with unique movement, interaction, and transformation rules.       |

Material Interactions

The simulation uses conditional rules to create interactions between particles:

* **Fire + Wood:** Fire spreads to adjacent wood.
* **Wood + Ash:** Burning wood eventually becomes ash.
* **Sand + Water:** Sand can fall through and displace water.
* **Acid + Materials:** Acid corrodes most materials it touches.
* **Acid + Metal:** Metal is resistant to acid.
* **Fire + Ash:** Ash does not burn.
* **Water + Wood:** Wood acts as a solid obstacle to water.

Fire also has a randomized lifespan, causing individual fire particles to eventually disappear.

How It Works

The simulation represents the world as a **2D integer array**, where each cell stores the type of particle occupying that location.

Each simulation step:

1. A random cell is selected.
2. The program checks which particle occupies the cell.
3. Movement or interaction rules are applied based on the particle type.
4. The grid is updated.
5. The Swing display renders the updated grid.

Controls

* Select a particle type using the buttons on the right.
* Click and drag on the simulation to place particles.
* Use the **Speed** slider to adjust the simulation speed.

Technologies

- Java
- Java Swing
- 2D Arrays
- Event-driven programming
- Randomized simulation

Files

* `SandLab.java` - simulation logic, particle behavior, and interactions
* `SandDisplay.java` - graphical interface, particle rendering, controls, and mouse interaction

