package table

import (
	"fmt"
	"sync"
	"time"

	"github.com/google/uuid"
	"go.uber.org/zap"
)

// TableState represents the state of a table
type TableState int

const (
	TableStateWaiting TableState = iota
	TableStateStarting
	TableStateDueling
	TableStateFinished
)

func (s TableState) String() string {
	switch s {
	case TableStateWaiting:
		return "WAITING"
	case TableStateStarting:
		return "STARTING"
	case TableStateDueling:
		return "DUELING"
	case TableStateFinished:
		return "FINISHED"
	default:
		return "UNKNOWN"
	}
}

// Seat represents a player seat at a table
type Seat struct {
	Number     int
	PlayerName string
	PlayerType string
	Locked     bool
	DeckValid  bool
}

// Table represents a game table
type Table struct {
	ID             string
	Name           string
	GameType       string
	ControllerName string
	RoomID         string
	State          TableState
	Seats          []*Seat
	NumSeats       int
	CreateTime     time.Time
	StartTime      *time.Time
	EndTime        *time.Time
	Tournament     bool
	TournamentID   string
	Password       string
	Spectators     []string
	mu             sync.RWMutex
}

// NewTable creates a new table
func NewTable(name, gameType, controllerName, roomID string, numSeats int) *Table {
	seats := make([]*Seat, numSeats)
	for i := 0; i < numSeats; i++ {
		seats[i] = &Seat{
			Number: i,
			Locked: false,
		}
	}

	return &Table{
		ID:             uuid.New().String(),
		Name:           name,
		GameType:       gameType,
		ControllerName: controllerName,
		RoomID:         roomID,
		State:          TableStateWaiting,
		Seats:          seats,
		NumSeats:       numSeats,
		CreateTime:     time.Now(),
		Spectators:     make([]string, 0),
	}
}

// AddPlayer adds a player to the table
func (t *Table) AddPlayer(playerName, playerType string) error {
	t.mu.Lock()
	defer t.mu.Unlock()

	if t.State != TableStateWaiting {
		return fmt.Errorf("table is not in waiting state")
	}

	// Find empty seat
	for _, seat := range t.Seats {
		if seat.PlayerName == "" && !seat.Locked {
			seat.PlayerName = playerName
			seat.PlayerType = playerType
			return nil
		}
	}

	return fmt.Errorf("no empty seats available")
}

// RemovePlayer removes a player from the table
func (t *Table) RemovePlayer(playerName string) error {
	t.mu.Lock()
	defer t.mu.Unlock()

	for _, seat := range t.Seats {
		if seat.PlayerName == playerName {
			seat.PlayerName = ""
			seat.PlayerType = ""
			seat.DeckValid = false
			return nil
		}
	}

	return fmt.Errorf("player not found at table")
}

// SwapSeats swaps two seats
func (t *Table) SwapSeats(seat1, seat2 int) error {
	t.mu.Lock()
	defer t.mu.Unlock()

	if seat1 < 0 || seat1 >= t.NumSeats || seat2 < 0 || seat2 >= t.NumSeats {
		return fmt.Errorf("invalid seat numbers")
	}

	// Swap player data
	t.Seats[seat1].PlayerName, t.Seats[seat2].PlayerName = t.Seats[seat2].PlayerName, t.Seats[seat1].PlayerName
	t.Seats[seat1].PlayerType, t.Seats[seat2].PlayerType = t.Seats[seat2].PlayerType, t.Seats[seat1].PlayerType
	t.Seats[seat1].DeckValid, t.Seats[seat2].DeckValid = t.Seats[seat2].DeckValid, t.Seats[seat1].DeckValid

	return nil
}

// IsFull checks if all seats are occupied
func (t *Table) IsFull() bool {
	t.mu.RLock()
	defer t.mu.RUnlock()

	for _, seat := range t.Seats {
		if seat.PlayerName == "" && !seat.Locked {
			return false
		}
	}
	return true
}

// GetPlayerCount returns the number of players
func (t *Table) GetPlayerCount() int {
	t.mu.RLock()
	defer t.mu.RUnlock()

	count := 0
	for _, seat := range t.Seats {
		if seat.PlayerName != "" {
			count++
		}
	}
	return count
}

// AddSpectator adds a spectator to the table
func (t *Table) AddSpectator(playerName string) {
	t.mu.Lock()
	defer t.mu.Unlock()

	t.Spectators = append(t.Spectators, playerName)
}

// RemoveSpectator removes a spectator from the table
func (t *Table) RemoveSpectator(playerName string) {
	t.mu.Lock()
	defer t.mu.Unlock()

	for i, name := range t.Spectators {
		if name == playerName {
			t.Spectators = append(t.Spectators[:i], t.Spectators[i+1:]...)
			return
		}
	}
}

// SetState sets the table state
func (t *Table) SetState(state TableState) {
	t.mu.Lock()
	defer t.mu.Unlock()

	t.State = state

	if state == TableStateStarting {
		now := time.Now()
		t.StartTime = &now
	} else if state == TableStateFinished {
		now := time.Now()
		t.EndTime = &now
	}
}

// GetState returns the current table state
func (t *Table) GetState() TableState {
	t.mu.RLock()
	defer t.mu.RUnlock()
	return t.State
}

// IsController checks if the given player is the table controller
func (t *Table) IsController(playerName string) bool {
	t.mu.RLock()
	defer t.mu.RUnlock()
	return t.ControllerName == playerName
}

// Manager manages game tables
type Manager struct {
	tables map[string]*Table
	mu     sync.RWMutex
	logger *zap.Logger
}

// NewManager creates a new table manager
func NewManager(logger *zap.Logger) *Manager {
	return &Manager{
		tables: make(map[string]*Table),
		logger: logger,
	}
}

// CreateTable creates a new table
func (m *Manager) CreateTable(name, gameType, controllerName, roomID string, numSeats int, password string) *Table {
	m.mu.Lock()
	defer m.mu.Unlock()

	table := NewTable(name, gameType, controllerName, roomID, numSeats)
	table.Password = password
	m.tables[table.ID] = table

	m.logger.Info("table created",
		zap.String("table_id", table.ID),
		zap.String("name", name),
		zap.String("game_type", gameType),
		zap.String("controller", controllerName),
		zap.Int("seats", numSeats),
	)

	return table
}

// GetTable retrieves a table by ID
func (m *Manager) GetTable(tableID string) (*Table, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()

	table, ok := m.tables[tableID]
	return table, ok
}

// RemoveTable removes a table
func (m *Manager) RemoveTable(tableID string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	delete(m.tables, tableID)

	m.logger.Info("table removed", zap.String("table_id", tableID))
}

// GetAllTables returns all tables
func (m *Manager) GetAllTables() []*Table {
	m.mu.RLock()
	defer m.mu.RUnlock()

	tables := make([]*Table, 0, len(m.tables))
	for _, table := range m.tables {
		tables = append(tables, table)
	}
	return tables
}

// GetTablesByRoom returns all tables in a room
func (m *Manager) GetTablesByRoom(roomID string) []*Table {
	m.mu.RLock()
	defer m.mu.RUnlock()

	tables := make([]*Table, 0)
	for _, table := range m.tables {
		if table.RoomID == roomID {
			tables = append(tables, table)
		}
	}
	return tables
}

// GetActiveTableCount returns the count of active tables
func (m *Manager) GetActiveTableCount() int {
	m.mu.RLock()
	defer m.mu.RUnlock()

	count := 0
	for _, table := range m.tables {
		if table.State != TableStateFinished {
			count++
		}
	}
	return count
}
