package session

import (
	"sync"
	"time"
)

// Session represents a user session
type Session struct {
	ID           string
	UserID       string
	Host         string
	IsAdmin      bool
	Connected    bool
	LastActivity time.Time
	LeasePeriod  time.Duration
	CallbackChan chan interface{} // Channel for WebSocket callbacks
	mu           sync.RWMutex
	reqMu        sync.Mutex // Prevents concurrent requests for same session
}

// NewSession creates a new session
func NewSession(id, host string, leasePeriod time.Duration) *Session {
	return &Session{
		ID:           id,
		Host:         host,
		Connected:    true,
		LastActivity: time.Now(),
		LeasePeriod:  leasePeriod,
		CallbackChan: make(chan interface{}, 100),
	}
}

// UpdateActivity updates the last activity timestamp
func (s *Session) UpdateActivity() {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.LastActivity = time.Now()
}

// IsExpired checks if the session has expired
func (s *Session) IsExpired() bool {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return time.Since(s.LastActivity) > s.LeasePeriod
}

// SendCallback sends a callback event to the session
// Returns false if the channel is full (timeout after 5 seconds)
func (s *Session) SendCallback(event interface{}) bool {
	select {
	case s.CallbackChan <- event:
		return true
	case <-time.After(5 * time.Second):
		return false // Timeout, client not reading
	}
}

// SetUserID sets the user ID for the session
func (s *Session) SetUserID(userID string) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.UserID = userID
}

// GetUserID gets the user ID for the session
func (s *Session) GetUserID() string {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return s.UserID
}

// SetAdmin sets the admin flag for the session
func (s *Session) SetAdmin(isAdmin bool) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.IsAdmin = isAdmin
}

// IsAdminSession checks if the session is an admin session
func (s *Session) IsAdminSession() bool {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return s.IsAdmin
}

// Disconnect marks the session as disconnected
func (s *Session) Disconnect() {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.Connected = false
}

// IsConnected checks if the session is connected
func (s *Session) IsConnected() bool {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return s.Connected
}

// Lock locks the session for exclusive request processing
func (s *Session) Lock() {
	s.reqMu.Lock()
}

// Unlock unlocks the session
func (s *Session) Unlock() {
	s.reqMu.Unlock()
}
