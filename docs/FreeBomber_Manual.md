# FreeBomber Manual (WIP)

## 1. 🚀 Getting Started

### System Requirements
- Java Development Kit (JDK) 8 or above
- Any IDE (e.g., IntelliJ, Eclipse) or terminal that can run Java programs
- Recommended screen resolution: 1280x720+

### How to Run the Game
1. Clone or download the project from the repo
2. Open the project in your IDE
3. Run `FreeBomber.java` from the `FreeBomber` package

### Controls
- Arrow Keys: Move player
- Spacebar: Plant bomb

---

## 2. 🎮 Game Mechanics

### Player Movement
- 4-directional grid movement
- Player size is 32x32 pixels (matches tile size)

### Bomb Planting & Explosion Timing
- Bomb has a countdown timer of 180 frames (~3 seconds at 60 FPS)
- Bomb explodes in a cross shape: center + up/down/left/right

### Collision & Damage Logic
- Explosions hit enemies and destructible tiles
- Player takes damage if they intersect the explosion tiles

### Enemy Types & Behavior
- `EnemyRock`, `EnemySlug`, etc. — move and follow paths or chase player
- Can be destroyed by bomb explosions

### Power-ups
_(To be added if implemented)_

---

## 3. 💣 Bomb System

### How Bombs Work
- When planted, bomb starts countdown (`timer = 180`)
- After countdown, `triggerExplosion()` is called

### Explosion Range & Animation Sync
- Explosion animation covers 5 tiles: center, up, down, left, right
- Collision logic updated to use 5 rectangles, matching animation exactly

### Collision Detection (New Logic)
```java
int tileSize = 32;
Rectangle[] explosionAreas = new Rectangle[] {
    new Rectangle(x, y, tileSize, tileSize),
    new Rectangle(x - tileSize, y, tileSize, tileSize),
    new Rectangle(x + tileSize, y, tileSize, tileSize),
    new Rectangle(x, y - tileSize, tileSize, tileSize),
    new Rectangle(x, y + tileSize, tileSize, tileSize)
};
```

### Bomb Powerups
_(To be added)_

---

## 4. 🧠 Code Architecture

### Project Structure
- `Characters/` — Player, Bombs, Enemies
- `Background/` — Game logic, Tile manager, Panels
- `FreeBomber/` — Main game launcher

### Key Classes
- `Bomb.java` — Controls bomb logic and animation
- `BombCollision.java` — Manages what gets hit
- `TileManager.java` — Destructible terrain
- `JackBomber.java` — The player character

### Adding a New Enemy
1. Create new class in `Characters/`
2. Extend from `Character`
3. Implement movement logic
4. Add to `Background.java` enemy list

### Modifying Explosion Radius
- Edit `triggerExplosion()` in `Bomb.java`
- Add extra rectangles in same direction for extended range

---

## 5. 🎨 Sprites & Animation

### File Locations
- `storage/bombs/` — Contains bomb and explosion frames

### Naming Convention
- Bomb: `time_cropped_bomb_X.png`
- Explosion: `explosion_cropped_[direction]X.png`

### Adding New Frames
- Add your frame image to correct folder
- Update `getBombImage()` method to include it

---

## 6. 🛠️ Troubleshooting & Tips

### Common Bugs
- Collision not aligned? Check `triggerExplosion()` hitboxes
- Enemy not dying? Confirm it's included in explosion checks

### Debug Tips
- Print statements in `update()` or `triggerExplosion()`
- Use colored rectangles to visualize collision zones

---

_Manual in progress. Ping Murat Can for rants, rage or rewrites._


# FreeBomber.FreeBomber

Freebomber is a fun and exciting video game where players must find their way through a dangerous maze to reach a hidden door and escape. The game is easy to learn but hard to master because every level brings new challenges, smarter enemies, and tough choices. Your goal is simple: find the door before your health runs out or time expires. But be careful! enemies, traps, and your own mistakes can stop you!

In Freebomber, you move up, down, left, or right on a grid-like map. Walls block some parts of the map you can’t pass, while bushes cover others you can walk through. The door to escape is always hidden, so you must explore carefully. Along the way, you’ll face enemies that chase you. These enemies are dangerous because you lose one of your three health points if they touch you. If your health drops to zero, the game ends.

To survive, you have two bombs you can use at any time. When you drop a bomb, it explodes after three seconds, destroying nearby bushes, enemies, or even hidden power-ups. But bombs are risky if you’re too close to the explosion, you’ll lose health too! After using a bomb, you must wait five seconds before using it again. As you progress through levels, your bombs become stronger and can blast through more areas, but enemies also grow faster and tougher.

The game rewards clever thinking. For example, bushes might hide power-ups like extra health, bigger bomb explosions, or temporary speed boosts. Do you take time to search for these helpful items, or do you rush to the exit? Sometimes, avoiding enemies is safer than fighting them. Other times, dropping a bomb to clear a path is the only way to survive.

