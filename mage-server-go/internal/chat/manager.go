package chat

import (
	"sync"
	"time"

	"go.uber.org/zap"
)

// Message represents a chat message
type Message struct {
	UserName  string
	Text      string
	Timestamp time.Time
	Color     string
	Type      string
}

// ChatRoom represents a chat room
type ChatRoom struct {
	ID       string
	messages []Message
	users    map[string]bool // username -> present
	mu       sync.RWMutex
	maxMessages int
}

// NewChatRoom creates a new chat room
func NewChatRoom(id string, maxMessages int) *ChatRoom {
	return &ChatRoom{
		ID:          id,
		messages:    make([]Message, 0),
		users:       make(map[string]bool),
		maxMessages: maxMessages,
	}
}

// AddMessage adds a message to the chat room
func (c *ChatRoom) AddMessage(msg Message) {
	c.mu.Lock()
	defer c.mu.Unlock()

	c.messages = append(c.messages, msg)

	// Keep only last N messages
	if len(c.messages) > c.maxMessages {
		c.messages = c.messages[len(c.messages)-c.maxMessages:]
	}
}

// GetMessages returns recent messages
func (c *ChatRoom) GetMessages(limit int) []Message {
	c.mu.RLock()
	defer c.mu.RUnlock()

	if limit == 0 || limit > len(c.messages) {
		limit = len(c.messages)
	}

	start := len(c.messages) - limit
	if start < 0 {
		start = 0
	}

	return append([]Message{}, c.messages[start:]...)
}

// AddUser adds a user to the chat room
func (c *ChatRoom) AddUser(username string) {
	c.mu.Lock()
	defer c.mu.Unlock()
	c.users[username] = true
}

// RemoveUser removes a user from the chat room
func (c *ChatRoom) RemoveUser(username string) {
	c.mu.Lock()
	defer c.mu.Unlock()
	delete(c.users, username)
}

// GetUsers returns list of users in the chat room
func (c *ChatRoom) GetUsers() []string {
	c.mu.RLock()
	defer c.mu.RUnlock()

	users := make([]string, 0, len(c.users))
	for user := range c.users {
		users = append(users, user)
	}
	return users
}

// Manager manages chat rooms
type Manager struct {
	rooms  map[string]*ChatRoom
	mu     sync.RWMutex
	logger *zap.Logger
}

// NewManager creates a new chat manager
func NewManager(logger *zap.Logger) *Manager {
	return &Manager{
		rooms:  make(map[string]*ChatRoom),
		logger: logger,
	}
}

// CreateRoom creates a new chat room
func (m *Manager) CreateRoom(id string) *ChatRoom {
	m.mu.Lock()
	defer m.mu.Unlock()

	room := NewChatRoom(id, 100) // Keep last 100 messages
	m.rooms[id] = room

	m.logger.Debug("chat room created", zap.String("room_id", id))

	return room
}

// GetRoom retrieves a chat room by ID
func (m *Manager) GetRoom(id string) (*ChatRoom, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()

	room, ok := m.rooms[id]
	return room, ok
}

// GetOrCreateRoom gets or creates a chat room
func (m *Manager) GetOrCreateRoom(id string) *ChatRoom {
	room, ok := m.GetRoom(id)
	if !ok {
		return m.CreateRoom(id)
	}
	return room
}

// RemoveRoom removes a chat room
func (m *Manager) RemoveRoom(id string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	delete(m.rooms, id)

	m.logger.Debug("chat room removed", zap.String("room_id", id))
}

// SendMessage sends a message to a chat room
func (m *Manager) SendMessage(roomID, username, text string) error {
	room, ok := m.GetRoom(roomID)
	if !ok {
		return nil // Room doesn't exist, ignore
	}

	msg := Message{
		UserName:  username,
		Text:      text,
		Timestamp: time.Now(),
		Color:     "BLACK",
		Type:      "TALK",
	}

	room.AddMessage(msg)

	// TODO: Broadcast message to all users in room via WebSocket
	// This would iterate through connected sessions and send callback

	return nil
}

// JoinRoom adds a user to a chat room
func (m *Manager) JoinRoom(roomID, username string) {
	room := m.GetOrCreateRoom(roomID)
	room.AddUser(username)

	m.logger.Debug("user joined chat room",
		zap.String("room_id", roomID),
		zap.String("username", username),
	)
}

// LeaveRoom removes a user from a chat room
func (m *Manager) LeaveRoom(roomID, username string) {
	room, ok := m.GetRoom(roomID)
	if !ok {
		return
	}

	room.RemoveUser(username)

	m.logger.Debug("user left chat room",
		zap.String("room_id", roomID),
		zap.String("username", username),
	)
}
