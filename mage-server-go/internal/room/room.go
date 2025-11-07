package room

import (
	"sync"

	"go.uber.org/zap"
)

// Room represents a game lobby/room
type Room struct {
	ID    string
	Name  string
	users map[string]bool // Connected users
	mu    sync.RWMutex
}

// NewRoom creates a new room
func NewRoom(id, name string) *Room {
	return &Room{
		ID:    id,
		Name:  name,
		users: make(map[string]bool),
	}
}

// AddUser adds a user to the room
func (r *Room) AddUser(username string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	r.users[username] = true
}

// RemoveUser removes a user from the room
func (r *Room) RemoveUser(username string) {
	r.mu.Lock()
	defer r.mu.Unlock()
	delete(r.users, username)
}

// GetUsers returns all users in the room
func (r *Room) GetUsers() []string {
	r.mu.RLock()
	defer r.mu.RUnlock()

	users := make([]string, 0, len(r.users))
	for user := range r.users {
		users = append(users, user)
	}
	return users
}

// GetUserCount returns the number of users in the room
func (r *Room) GetUserCount() int {
	r.mu.RLock()
	defer r.mu.RUnlock()
	return len(r.users)
}

// Manager manages game rooms
type Manager struct {
	mainRoom *Room
	rooms    map[string]*Room
	mu       sync.RWMutex
	logger   *zap.Logger
}

// NewManager creates a new room manager
func NewManager(logger *zap.Logger) *Manager {
	mainRoom := NewRoom("main", "Main Lobby")

	return &Manager{
		mainRoom: mainRoom,
		rooms:    map[string]*Room{"main": mainRoom},
		logger:   logger,
	}
}

// GetMainRoom returns the main lobby room
func (m *Manager) GetMainRoom() *Room {
	return m.mainRoom
}

// GetMainRoomID returns the main room ID
func (m *Manager) GetMainRoomID() string {
	return m.mainRoom.ID
}

// CreateRoom creates a new room
func (m *Manager) CreateRoom(id, name string) *Room {
	m.mu.Lock()
	defer m.mu.Unlock()

	room := NewRoom(id, name)
	m.rooms[id] = room

	m.logger.Info("room created",
		zap.String("room_id", id),
		zap.String("name", name),
	)

	return room
}

// GetRoom retrieves a room by ID
func (m *Manager) GetRoom(id string) (*Room, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()

	room, ok := m.rooms[id]
	return room, ok
}

// RemoveRoom removes a room
func (m *Manager) RemoveRoom(id string) {
	if id == "main" {
		return // Can't remove main room
	}

	m.mu.Lock()
	defer m.mu.Unlock()

	delete(m.rooms, id)

	m.logger.Info("room removed", zap.String("room_id", id))
}

// UserJoinRoom adds a user to a room
func (m *Manager) UserJoinRoom(roomID, username string) error {
	room, ok := m.GetRoom(roomID)
	if !ok {
		return nil // Room doesn't exist
	}

	room.AddUser(username)

	m.logger.Debug("user joined room",
		zap.String("room_id", roomID),
		zap.String("username", username),
	)

	return nil
}

// UserLeaveRoom removes a user from a room
func (m *Manager) UserLeaveRoom(roomID, username string) {
	room, ok := m.GetRoom(roomID)
	if !ok {
		return
	}

	room.RemoveUser(username)

	m.logger.Debug("user left room",
		zap.String("room_id", roomID),
		zap.String("username", username),
	)
}

// GetRoomUsers returns all users in a room
func (m *Manager) GetRoomUsers(roomID string) []string {
	room, ok := m.GetRoom(roomID)
	if !ok {
		return []string{}
	}

	return room.GetUsers()
}
