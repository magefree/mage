package user

import (
	"context"
	"fmt"
	"sync"
	"time"

	"github.com/magefree/mage-server-go/internal/auth"
	"github.com/magefree/mage-server-go/internal/config"
	"github.com/magefree/mage-server-go/internal/repository"
	"go.uber.org/zap"
)

// Manager manages users
type Manager interface {
	Register(ctx context.Context, username, password, email string) error
	Authenticate(ctx context.Context, username, password string) (*repository.User, error)
	GetByName(ctx context.Context, username string) (*repository.User, error)
	UserConnect(ctx context.Context, username, sessionID string)
	UserDisconnect(ctx context.Context, sessionID string)
	GetConnectedUsers() []string
	IsUserConnected(username string) bool
	LockUser(ctx context.Context, username string, duration time.Duration) error
	MuteUser(ctx context.Context, username string, duration time.Duration) error
	ActivateUser(ctx context.Context, username string) error
	DeactivateUser(ctx context.Context, username string) error
}

type manager struct {
	repo           *repository.UserRepository
	statsRepo      *repository.StatsRepository
	validator      *Validator
	cfg            config.ValidationConfig
	logger         *zap.Logger
	connectedUsers map[string]string // sessionID -> username
	mu             sync.RWMutex
}

// NewManager creates a new user manager
func NewManager(repo *repository.UserRepository, statsRepo *repository.StatsRepository, cfg config.ValidationConfig, logger *zap.Logger) Manager {
	return &manager{
		repo:           repo,
		statsRepo:      statsRepo,
		validator:      NewValidator(cfg),
		cfg:            cfg,
		logger:         logger,
		connectedUsers: make(map[string]string),
	}
}

// Register registers a new user
func (m *manager) Register(ctx context.Context, username, password, email string) error {
	// Validate input
	if err := m.validator.ValidateUsername(username); err != nil {
		return err
	}

	if err := m.validator.ValidatePassword(password); err != nil {
		return err
	}

	if err := m.validator.ValidateEmail(email); err != nil {
		return err
	}

	// Check if user already exists
	exists, err := m.repo.Exists(ctx, username)
	if err != nil {
		return fmt.Errorf("failed to check user existence: %w", err)
	}
	if exists {
		return fmt.Errorf("username already taken")
	}

	// Hash password
	passwordHash, err := auth.HashPassword(password)
	if err != nil {
		return fmt.Errorf("failed to hash password: %w", err)
	}

	// Create user
	u := &repository.User{
		Name:     username,
		Password: passwordHash,
		Email:    email,
		Active:   true,
	}

	if err := m.repo.Create(ctx, u); err != nil {
		return fmt.Errorf("failed to create user: %w", err)
	}

	// Create user stats
	stats := &repository.UserStats{
		UserName:        username,
		Rating:          1500.0,
		RatingDeviation: 350.0,
		Volatility:      0.06,
	}

	if err := m.statsRepo.Create(ctx, stats); err != nil {
		m.logger.Error("failed to create user stats", zap.Error(err), zap.String("username", username))
		// Don't fail registration if stats creation fails
	}

	m.logger.Info("user registered",
		zap.String("username", username),
		zap.String("email", email),
	)

	return nil
}

// Authenticate authenticates a user
func (m *manager) Authenticate(ctx context.Context, username, password string) (*repository.User, error) {
	// Get user from database
	u, err := m.repo.GetByName(ctx, username)
	if err != nil {
		return nil, fmt.Errorf("authentication failed: invalid credentials")
	}

	// Check if user is active
	if !u.Active {
		return nil, fmt.Errorf("account is deactivated")
	}

	// Check if user is locked
	if u.IsLocked() {
		return nil, fmt.Errorf("account is locked until %s", u.LockEndTime.Format(time.RFC3339))
	}

	// Verify password
	if !auth.VerifyPassword(password, u.Password) {
		return nil, fmt.Errorf("authentication failed: invalid credentials")
	}

	return u, nil
}

// GetByName retrieves a user by username
func (m *manager) GetByName(ctx context.Context, username string) (*repository.User, error) {
	return m.repo.GetByName(ctx, username)
}

// UserConnect marks a user as connected
func (m *manager) UserConnect(ctx context.Context, username, sessionID string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	m.connectedUsers[sessionID] = username

	m.logger.Debug("user connected",
		zap.String("username", username),
		zap.String("session_id", sessionID),
	)
}

// UserDisconnect marks a user as disconnected
func (m *manager) UserDisconnect(ctx context.Context, sessionID string) {
	m.mu.Lock()
	defer m.mu.Unlock()

	if username, ok := m.connectedUsers[sessionID]; ok {
		delete(m.connectedUsers, sessionID)

		m.logger.Debug("user disconnected",
			zap.String("username", username),
			zap.String("session_id", sessionID),
		)
	}
}

// GetConnectedUsers returns a list of connected usernames
func (m *manager) GetConnectedUsers() []string {
	m.mu.RLock()
	defer m.mu.RUnlock()

	users := make([]string, 0, len(m.connectedUsers))
	seen := make(map[string]bool)

	for _, username := range m.connectedUsers {
		if !seen[username] {
			users = append(users, username)
			seen[username] = true
		}
	}

	return users
}

// IsUserConnected checks if a user is connected
func (m *manager) IsUserConnected(username string) bool {
	m.mu.RLock()
	defer m.mu.RUnlock()

	for _, u := range m.connectedUsers {
		if u == username {
			return true
		}
	}
	return false
}

// LockUser locks a user account
func (m *manager) LockUser(ctx context.Context, username string, duration time.Duration) error {
	u, err := m.repo.GetByName(ctx, username)
	if err != nil {
		return err
	}

	lockEnd := time.Now().Add(duration)
	u.LockEndTime = &lockEnd

	if err := m.repo.Update(ctx, u); err != nil {
		return err
	}

	m.logger.Info("user locked",
		zap.String("username", username),
		zap.Duration("duration", duration),
	)

	return nil
}

// MuteUser mutes a user's chat
func (m *manager) MuteUser(ctx context.Context, username string, duration time.Duration) error {
	u, err := m.repo.GetByName(ctx, username)
	if err != nil {
		return err
	}

	muteEnd := time.Now().Add(duration)
	u.ChatLockEndTime = &muteEnd

	if err := m.repo.Update(ctx, u); err != nil {
		return err
	}

	m.logger.Info("user muted",
		zap.String("username", username),
		zap.Duration("duration", duration),
	)

	return nil
}

// ActivateUser activates a user account
func (m *manager) ActivateUser(ctx context.Context, username string) error {
	u, err := m.repo.GetByName(ctx, username)
	if err != nil {
		return err
	}

	u.Active = true
	u.LockEndTime = nil

	if err := m.repo.Update(ctx, u); err != nil {
		return err
	}

	m.logger.Info("user activated", zap.String("username", username))
	return nil
}

// DeactivateUser deactivates a user account
func (m *manager) DeactivateUser(ctx context.Context, username string) error {
	u, err := m.repo.GetByName(ctx, username)
	if err != nil {
		return err
	}

	u.Active = false

	if err := m.repo.Update(ctx, u); err != nil {
		return err
	}

	m.logger.Info("user deactivated", zap.String("username", username))
	return nil
}
