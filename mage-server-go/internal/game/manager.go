package game

import (
	"fmt"
	"sync"
	"time"

	"github.com/google/uuid"
	"go.uber.org/zap"
)

// GameState represents the state of a game
type GameState int

const (
	GameStateStarting GameState = iota
	GameStateInProgress
	GameStatePaused
	GameStateFinished
)

func (s GameState) String() string {
	switch s {
	case GameStateStarting:
		return "STARTING"
	case GameStateInProgress:
		return "IN_PROGRESS"
	case GameStatePaused:
		return "PAUSED"
	case GameStateFinished:
		return "FINISHED"
	default:
		return "UNKNOWN"
	}
}

// PlayerAction represents a player action in the game
type PlayerAction struct {
	PlayerID   string
	ActionType string
	Data       interface{}
	Timestamp  time.Time
}

// Game represents a game instance
type Game struct {
	ID              string
	TableID         string
	GameType        string
	State           GameState
	Players         []string
	ActivePlayerID  string
	PriorityPlayer  string
	Turn            int
	StartTime       time.Time
	EndTime         *time.Time
	Winner          string
	ActionQueue     chan PlayerAction
	Watchers        map[string]bool
	mu              sync.RWMutex
}

// NewGame creates a new game instance
func NewGame(tableID, gameType string, players []string) *Game {
	return &Game{
		ID:          uuid.New().String(),
		TableID:     tableID,
		GameType:    gameType,
		State:       GameStateStarting,
		Players:     players,
		Turn:        1,
		StartTime:   time.Now(),
		ActionQueue: make(chan PlayerAction, 100),
		Watchers:    make(map[string]bool),
	}
}

// AddWatcher adds a watcher to the game
func (g *Game) AddWatcher(playerName string) {
	g.mu.Lock()
	defer g.mu.Unlock()
	g.Watchers[playerName] = true
}

// RemoveWatcher removes a watcher from the game
func (g *Game) RemoveWatcher(playerName string) {
	g.mu.Lock()
	defer g.mu.Unlock()
	delete(g.Watchers, playerName)
}

// GetWatchers returns all watchers
func (g *Game) GetWatchers() []string {
	g.mu.RLock()
	defer g.mu.RUnlock()

	watchers := make([]string, 0, len(g.Watchers))
	for watcher := range g.Watchers {
		watchers = append(watchers, watcher)
	}
	return watchers
}

// SetState sets the game state
func (g *Game) SetState(state GameState) {
	g.mu.Lock()
	defer g.mu.Unlock()

	g.State = state
	if state == GameStateFinished {
		now := time.Now()
		g.EndTime = &now
	}
}

// GetState returns the current game state
func (g *Game) GetState() GameState {
	g.mu.RLock()
	defer g.mu.RUnlock()
	return g.State
}

// IsPlayer checks if the given player is in the game
func (g *Game) IsPlayer(playerName string) bool {
	g.mu.RLock()
	defer g.mu.RUnlock()

	for _, player := range g.Players {
		if player == playerName {
			return true
		}
	}
	return false
}

// Manager manages game instances
type Manager struct {
	games     map[string]*Game
	gamesByTable map[string]string // tableID -> gameID
	mu        sync.RWMutex
	logger    *zap.Logger
}

// NewManager creates a new game manager
func NewManager(logger *zap.Logger) *Manager {
	return &Manager{
		games:        make(map[string]*Game),
		gamesByTable: make(map[string]string),
		logger:       logger,
	}
}

// CreateGame creates a new game
func (m *Manager) CreateGame(tableID, gameType string, players []string) *Game {
	m.mu.Lock()
	defer m.mu.Unlock()

	game := NewGame(tableID, gameType, players)
	m.games[game.ID] = game
	m.gamesByTable[tableID] = game.ID

	m.logger.Info("game created",
		zap.String("game_id", game.ID),
		zap.String("table_id", tableID),
		zap.String("game_type", gameType),
		zap.Strings("players", players),
	)

	return game
}

// GetGame retrieves a game by ID
func (m *Manager) GetGame(gameID string) (*Game, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()

	game, ok := m.games[gameID]
	return game, ok
}

// GetGameByTable retrieves a game by table ID
func (m *Manager) GetGameByTable(tableID string) (*Game, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()

	gameID, ok := m.gamesByTable[tableID]
	if !ok {
		return nil, false
	}

	game, ok := m.games[gameID]
	return game, ok
}

// RemoveGame removes a game
func (m *Manager) RemoveGame(gameID string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	if game, ok := m.games[gameID]; ok {
		delete(m.gamesByTable, game.TableID)
		close(game.ActionQueue)
		delete(m.games, gameID)

		m.logger.Info("game removed", zap.String("game_id", gameID))
	}
}

// GetActiveGames returns all active games
func (m *Manager) GetActiveGames() []*Game {
	m.mu.RLock()
	defer m.mu.RUnlock()

	games := make([]*Game, 0)
	for _, game := range m.games {
		if game.State != GameStateFinished {
			games = append(games, game)
		}
	}
	return games
}

// GetActiveGameCount returns the count of active games
func (m *Manager) GetActiveGameCount() int {
	m.mu.RLock()
	defer m.mu.RUnlock()

	count := 0
	for _, game := range m.games {
		if game.State != GameStateFinished {
			count++
		}
	}
	return count
}

// SendPlayerAction sends a player action to a game
func (m *Manager) SendPlayerAction(gameID, playerID, actionType string, data interface{}) error {
	game, ok := m.GetGame(gameID)
	if !ok {
		return fmt.Errorf("game not found")
	}

	if !game.IsPlayer(playerID) {
		return fmt.Errorf("player not in game")
	}

	action := PlayerAction{
		PlayerID:   playerID,
		ActionType: actionType,
		Data:       data,
		Timestamp:  time.Now(),
	}

	select {
	case game.ActionQueue <- action:
		return nil
	default:
		return fmt.Errorf("action queue full")
	}
}

// GameEngine interface defines the contract for game engine implementations
// This allows the Go server to integrate with different game engines
type GameEngine interface {
	// StartGame initializes and starts a game
	StartGame(gameID string, players []string, gameType string) error

	// ProcessAction processes a player action
	ProcessAction(gameID string, action PlayerAction) error

	// GetGameView returns the current game view for a player
	GetGameView(gameID, playerID string) (interface{}, error)

	// EndGame ends a game
	EndGame(gameID string, winner string) error

	// PauseGame pauses a game
	PauseGame(gameID string) error

	// ResumeGame resumes a paused game
	ResumeGame(gameID string) error
}

// EngineAdapter adapts the game engine to the server
type EngineAdapter struct {
	engine GameEngine
	logger *zap.Logger
}

// NewEngineAdapter creates a new engine adapter
func NewEngineAdapter(engine GameEngine, logger *zap.Logger) *EngineAdapter {
	return &EngineAdapter{
		engine: engine,
		logger: logger,
	}
}

// ProcessGameActions processes actions from a game's action queue
func (ea *EngineAdapter) ProcessGameActions(game *Game) {
	for action := range game.ActionQueue {
		if err := ea.engine.ProcessAction(game.ID, action); err != nil {
			ea.logger.Error("failed to process action",
				zap.String("game_id", game.ID),
				zap.String("player_id", action.PlayerID),
				zap.Error(err),
			)
		}
	}
}
