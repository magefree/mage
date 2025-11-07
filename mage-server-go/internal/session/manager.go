package session

import (
	"context"
	"sync"
	"time"

	"go.uber.org/zap"
)

// Manager manages user sessions
type Manager interface {
	CreateSession(id, host string) *Session
	GetSession(id string) (*Session, bool)
	RemoveSession(id string)
	ValidateSession(id string) bool
	UpdateActivity(id string)
	CleanupExpiredSessions(ctx context.Context)
	GetActiveSessions() int
	GetSessionsByUser(userID string) []*Session
	CloseAll()
}

type manager struct {
	sessions    map[string]*Session
	mu          sync.RWMutex
	leasePeriod time.Duration
	logger      *zap.Logger
}

// NewManager creates a new session manager
func NewManager(leasePeriod time.Duration, logger *zap.Logger) Manager {
	return &manager{
		sessions:    make(map[string]*Session),
		leasePeriod: leasePeriod,
		logger:      logger,
	}
}

// CreateSession creates a new session
func (m *manager) CreateSession(id, host string) *Session {
	m.mu.Lock()
	defer m.mu.Unlock()

	sess := NewSession(id, host, m.leasePeriod)
	m.sessions[id] = sess

	m.logger.Debug("session created",
		zap.String("session_id", id),
		zap.String("host", host),
	)

	return sess
}

// GetSession retrieves a session by ID
func (m *manager) GetSession(id string) (*Session, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	sess, ok := m.sessions[id]
	return sess, ok
}

// RemoveSession removes a session by ID
func (m *manager) RemoveSession(id string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	if sess, ok := m.sessions[id]; ok {
		close(sess.CallbackChan)
		delete(m.sessions, id)

		m.logger.Debug("session removed",
			zap.String("session_id", id),
		)
	}
}

// ValidateSession checks if a session exists and is valid
func (m *manager) ValidateSession(id string) bool {
	sess, ok := m.GetSession(id)
	if !ok {
		return false
	}
	return !sess.IsExpired()
}

// UpdateActivity updates the last activity timestamp for a session
func (m *manager) UpdateActivity(id string) {
	if sess, ok := m.GetSession(id); ok {
		sess.UpdateActivity()
	}
}

// CleanupExpiredSessions runs a goroutine that periodically cleans up expired sessions
func (m *manager) CleanupExpiredSessions(ctx context.Context) {
	ticker := time.NewTicker(m.leasePeriod / 2)
	defer ticker.Stop()

	m.logger.Info("session cleanup goroutine started",
		zap.Duration("interval", m.leasePeriod/2),
	)

	for {
		select {
		case <-ctx.Done():
			m.logger.Info("session cleanup goroutine stopped")
			return
		case <-ticker.C:
			m.cleanupExpired()
		}
	}
}

// cleanupExpired removes all expired sessions
func (m *manager) cleanupExpired() {
	m.mu.Lock()
	defer m.mu.Unlock()

	expired := make([]string, 0)
	for id, sess := range m.sessions {
		if sess.IsExpired() {
			expired = append(expired, id)
			close(sess.CallbackChan)
			delete(m.sessions, id)
		}
	}

	if len(expired) > 0 {
		m.logger.Info("cleaned up expired sessions",
			zap.Int("count", len(expired)),
		)
	}
}

// GetActiveSessions returns the count of active sessions
func (m *manager) GetActiveSessions() int {
	m.mu.RLock()
	defer m.mu.RUnlock()
	return len(m.sessions)
}

// GetSessionsByUser returns all sessions for a given user ID
func (m *manager) GetSessionsByUser(userID string) []*Session {
	m.mu.RLock()
	defer m.mu.RUnlock()

	sessions := make([]*Session, 0)
	for _, sess := range m.sessions {
		if sess.GetUserID() == userID {
			sessions = append(sessions, sess)
		}
	}
	return sessions
}

// CloseAll closes all sessions
func (m *manager) CloseAll() {
	m.mu.Lock()
	defer m.mu.Unlock()

	for id, sess := range m.sessions {
		sess.Disconnect()
		close(sess.CallbackChan)
		delete(m.sessions, id)
	}

	m.logger.Info("all sessions closed",
		zap.Int("count", len(m.sessions)),
	)
}
