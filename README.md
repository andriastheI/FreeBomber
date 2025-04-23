# FreeBomber

<img src="src/storage/logo/logo.png" alt="FreeBomber Logo" width="300"/>

---

### Project Description

**FreeBomber** is a Java-based 2D game inspired by the classic Bomberman. It features a fully interactive grid-based environment where players plant bombs to destroy enemies and walls, with the ultimate goal of reaching the exit door. It serves as a great example of object-oriented programming, multi-threading, real-time rendering with Java Swing, and game state management.

The game includes:
- Multiple levels with increasing difficulty
- Dynamic enemies with smart AI (some react to player proximity)
- Bomb logic with explosion propagation
- Collectibles, power-ups, and cheat-code support
- A high score tracking system and user scoreboard

---

### UML Diagram:

![FreeBomber UML DIagram](docs/UML.png)

---

### Constraints:

- Bombs can be planted only up to a defined limit
- Each explosion affects nearby tiles and enemies
- Enemies move autonomously and check for collision with environment
- The player must navigate to the door after clearing obstructions

---

### Requirements and how they were met...

- 2 Group members: Murat Guzelocak and Molly O'Connor
- The solution is uploaded to **GitHub**, and the **repository link was emailed** to the professor **before the presentation**.
- Each group member:
  - Contributed to both the code and the presentation.
  - Speaks during the presentation, explaining either the problem or the group’s solution.
- The **Presentation duration** is 10–20 minutes
  - Must include an explanation of the game mechanics and implementation strategy.

---

### Challenges 

One of the biggest challenges we faced was coordinating bomb behavior, explosion propagation, and managing collisions with both static (walls) and dynamic (enemies, player) entities. Another was designing enemy movement patterns that feel intelligent but are also fair and non-repetitive.

---

### Unique Features 

Our implementation goes beyond basic gameplay with:

- **Named Characters**: Dynamic user input for player name, plus randomly named enemies
- **Cheat Code Support**: Launch directly into level 5 using `nwilliams` as a command-line argument (extra credit??)
- **Real-Time High Score System**: Reads and writes scores dynamically to file
- **Fully GUI-Based**: Watch all interactions in real-time with explosion animations, movement, and effects
- **Power-Ups**: Gain abilities like faster movement, bomb kicking, and more

---

### How to Run the Project

1. Clone the Repository
```bash
git clone <https://github.com/andriastheI/FreeBomber.git>
```

2. Compile and Run the Project

Using **Ant**:
```bash
cd FreeBomber
ant run
```
