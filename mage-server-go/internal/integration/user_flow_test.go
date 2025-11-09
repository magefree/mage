package integration

import (
	"context"
	"testing"
	"time"

	"github.com/magefree/mage-server-go/internal/auth"
	"github.com/magefree/mage-server-go/internal/config"
	"github.com/magefree/mage-server-go/internal/repository"
	"github.com/magefree/mage-server-go/internal/session"
	"github.com/magefree/mage-server-go/internal/user"
	"go.uber.org/zap/zaptest"
)

// TestUserRegistrationAndAuth tests the complete user registration and authentication flow
func TestUserRegistrationAndAuth(t *testing.T) {
	logger := zaptest.NewLogger(t)
	ctx := context.Background()

	// Create mock database (in-memory for testing)
	db := &repository.DB{} // TODO: Need actual DB connection for full integration

	// Initialize repositories
	userRepo := repository.NewUserRepository(db)
	statsRepo := repository.NewStatsRepository(db)

	// Initialize validation config
	validationCfg := config.ValidationConfig{
		Username: config.UsernameValidation{
			MinLength: 3,
			MaxLength: 20,
			Pattern:   "^[a-zA-Z0-9_]+$",
		},
		Password: config.PasswordValidation{
			MinLength: 8,
			MaxLength: 100,
		},
	}

	// Initialize user manager
	userMgr := user.NewManager(userRepo, statsRepo, validationCfg, logger)

	// Test 1: Username validation
	t.Run("ValidateUsername", func(t *testing.T) {
		validator := user.NewValidator(validationCfg)

		// Too short
		if err := validator.ValidateUsername("ab"); err == nil {
			t.Error("expected error for username too short")
		}

		// Too long
		if err := validator.ValidateUsername("verylongusernamethatexceedslimit"); err == nil {
			t.Error("expected error for username too long")
		}

		// Invalid characters
		if err := validator.ValidateUsername("user@name"); err == nil {
			t.Error("expected error for invalid characters")
		}

		// Valid username
		if err := validator.ValidateUsername("testuser"); err != nil {
			t.Errorf("expected valid username, got error: %v", err)
		}
	})

	// Test 2: Password validation
	t.Run("ValidatePassword", func(t *testing.T) {
		validator := user.NewValidator(validationCfg)

		// Too short
		if err := validator.ValidatePassword("short"); err == nil {
			t.Error("expected error for password too short")
		}

		// Valid password
		if err := validator.ValidatePassword("securepassword123"); err != nil {
			t.Errorf("expected valid password, got error: %v", err)
		}
	})

	// Test 3: Password hashing
	t.Run("PasswordHashing", func(t *testing.T) {
		password := "mySecurePassword123"

		// Hash password
		hash, err := auth.HashPassword(password)
		if err != nil {
			t.Fatalf("failed to hash password: %v", err)
		}

		// Verify correct password
		if !auth.VerifyPassword(password, hash) {
			t.Error("password verification failed for correct password")
		}

		// Verify incorrect password
		if auth.VerifyPassword("wrongpassword", hash) {
			t.Error("password verification succeeded for incorrect password")
		}
	})

	// Note: Registration and authentication require actual database
	// These tests would fail without a real DB connection
	_ = userMgr
	_ = ctx
}

// TestSessionFlow tests session creation, validation, and expiration
func TestSessionFlow(t *testing.T) {
	logger := zaptest.NewLogger(t)

	// Initialize session manager with short lease for testing
	sessionMgr := session.NewManager(500*time.Millisecond, logger)

	t.Run("CreateAndValidateSession", func(t *testing.T) {
		// Create session
		sess := sessionMgr.CreateSession("test-session-1", "localhost")
		if sess == nil {
			t.Fatal("failed to create session")
		}

		// Session should be valid immediately
		if !sessionMgr.ValidateSession("test-session-1") {
			t.Error("newly created session should be valid")
		}

		// Set user ID
		sess.SetUserID("testuser")
		if sess.GetUserID() != "testuser" {
			t.Errorf("expected user ID 'testuser', got '%s'", sess.GetUserID())
		}
	})

	t.Run("SessionExpiration", func(t *testing.T) {
		// Create session with short lease
		sess := sessionMgr.CreateSession("test-session-2", "localhost")

		// Should be valid initially
		if !sessionMgr.ValidateSession("test-session-2") {
			t.Error("session should be valid initially")
		}

		// Wait for expiration
		time.Sleep(600 * time.Millisecond)

		// Should be expired
		if sessionMgr.ValidateSession("test-session-2") {
			t.Error("session should be expired")
		}

		// Session object should report expired
		if !sess.IsExpired() {
			t.Error("session.IsExpired() should return true")
		}
	})

	t.Run("SessionActivityUpdate", func(t *testing.T) {
		sess := sessionMgr.CreateSession("test-session-3", "localhost")

		// Wait half the lease period
		time.Sleep(300 * time.Millisecond)

		// Update activity
		sess.UpdateActivity()

		// Wait another half period (would be expired without update)
		time.Sleep(300 * time.Millisecond)

		// Should still be valid due to activity update
		if sessionMgr.ValidateSession("test-session-3") {
			// This is expected - activity was updated
		} else {
			t.Error("session should be valid after activity update")
		}
	})

	t.Run("AdminSession", func(t *testing.T) {
		sess := sessionMgr.CreateSession("test-session-4", "localhost")

		// Initially not admin
		if sess.IsAdminSession() {
			t.Error("session should not be admin initially")
		}

		// Set admin
		sess.SetAdmin(true)

		// Should be admin now
		if !sess.IsAdminSession() {
			t.Error("session should be admin after SetAdmin(true)")
		}
	})
}

// TestTokenStore tests password reset token generation and validation
func TestTokenStore(t *testing.T) {
	tokenStore := auth.NewTokenStore(1 * time.Second)

	t.Run("GenerateAndValidateToken", func(t *testing.T) {
		email := "test@example.com"

		// Generate token
		token, err := tokenStore.GenerateToken(email)
		if err != nil {
			t.Fatalf("failed to generate token: %v", err)
		}

		// Token should be 6 digits
		if len(token) != 6 {
			t.Errorf("expected 6-digit token, got %d digits", len(token))
		}

		// Verify token
		if !tokenStore.VerifyToken(email, token) {
			t.Error("token verification failed for valid token")
		}

		// Verify wrong token
		if tokenStore.VerifyToken(email, "000000") {
			t.Error("token verification succeeded for wrong token")
		}
	})

	t.Run("ConsumeToken", func(t *testing.T) {
		email := "test2@example.com"
		token, _ := tokenStore.GenerateToken(email)

		// Consume token (should succeed)
		if !tokenStore.ConsumeToken(email, token) {
			t.Error("token consumption failed")
		}

		// Try to consume again (should fail - one-time use)
		if tokenStore.ConsumeToken(email, token) {
			t.Error("token should only be consumable once")
		}
	})

	t.Run("TokenExpiration", func(t *testing.T) {
		email := "test3@example.com"
		token, _ := tokenStore.GenerateToken(email)

		// Wait for expiration
		time.Sleep(1100 * time.Millisecond)

		// Token should be expired
		if tokenStore.VerifyToken(email, token) {
			t.Error("expired token should not be valid")
		}
	})
}
