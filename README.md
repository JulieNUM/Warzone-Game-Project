# Warzone-Game-Project# 

Warzone is a strategy board game based on the famous game **Risk**, featuring different map formats, player strategies, and game modes. The project implements essential game mechanics, including map loading, player behavior, and game phases.

## Project Features

### 1. **Map Formats**
- **Conquest Map Format**: Supports a custom map format for continent and territory data, including adjacency relationships.
- **Domination Map Format**: Supports the traditional Risk map format.
- **Automatic Format Detection**: The game automatically detects the map format (Conquest or Domination) based on the file content, eliminating the need for file extensions.

### 2. **Design Patterns**
- **Adapter Pattern**: The game uses the Adapter pattern to handle different map formats, ensuring a unified interface for loading and saving maps.
  - `DominationMapFileAdapter` for the Domination format.
  - `ConquestMapFileAdapter` for the Conquest format.
- **Strategy Pattern**: Implements various player strategies (Human, Aggressive, Benevolent, Random, Cheater) using the Strategy pattern to alter player behaviors dynamically.

### 3. **Game Phases and Modes**
- **Game Phases**: The game progresses through multiple phases, such as map editing, order creation, order execution, and tournament play.
- **Single and Tournament Modes**: Supports both single-player and tournament gameplay, allowing players to engage in a broader set of challenges.
- **Automatic Gameplay**: An option for autoplay mode that simulates the game with predefined steps, useful for testing and simulation.

### 4. **Game Save/Load Functionality**
- **Game State Serialization**: The game state (including player data, map data, and game phase) is saved in JSON format. The game can be saved and loaded seamlessly using the `GameEngine` class.
  - Uses Jackson's `ObjectMapper` to serialize and deserialize game objects.
- **Game Save and Load**: The game engine (`GameEngine`) is responsible for saving and loading the entire game state, which includes player actions, map data, and game phases.

### 5. **Player Actions and Strategies**
- **Player Command Queue**: Players can issue commands such as deploying armies, advancing to neighboring countries, and attacking opponents.
- **Multiple Player Strategies**:
  - **Human**: A human player makes decisions through a user interface.
  - **Aggressive**: An aggressive player takes the most territory.
  - **Benevolent**: A benevolent player avoids attacking and aids others.
  - **Random**: A random player makes unpredictable moves.
  - **Cheater**: A cheater player tries to break the rules of the game.
  
### 6. **Map Data and Validation**
- **Map Cleanup**: Every time a new map is loaded, all old data is cleaned up to prevent conflicts with previous game states.
- **Map Validation**: Ensures that the map data (countries, continents, and adjacencies) are valid and connected. Any issues are flagged before gameplay begins.


