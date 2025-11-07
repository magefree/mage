package session

import (
	"testing"
	"time"

	"go.uber.org/zap/zaptest"
)

func TestSessionCreation(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(5*time.Minute, logger)

	sess := mgr.CreateSession("test-session-1", "localhost")
	if sess == nil {
		t.Fatal("expected session to be created")
	}

	if sess.ID != "test-session-1" {
		t.Errorf("expected session ID 'test-session-1', got '%s'", sess.ID)
	}

	if sess.Host != "localhost" {
		t.Errorf("expected host 'localhost', got '%s'", sess.Host)
	}
}

func TestSessionValidation(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(5*time.Minute, logger)

	// Create session
	sess := mgr.CreateSession("test-session-2", "localhost")
	if sess == nil {
		t.Fatal("failed to create session")
	}

	// Validate session
	if !mgr.ValidateSession("test-session-2") {
		t.Error("expected session to be valid")
	}

	// Invalid session
	if mgr.ValidateSession("non-existent") {
		t.Error("expected non-existent session to be invalid")
	}
}

func TestSessionExpiration(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(100*time.Millisecond, logger)

	sess := mgr.CreateSession("test-session-3", "localhost")
	if sess == nil {
		t.Fatal("failed to create session")
	}

	// Session should be valid immediately
	if !mgr.ValidateSession("test-session-3") {
		t.Error("expected session to be valid")
	}

	// Wait for expiration
	time.Sleep(150 * time.Millisecond)

	// Session should be expired
	if sess.IsExpired() != true {
		t.Error("expected session to be expired")
	}
}

func TestSessionUserID(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(5*time.Minute, logger)

	sess := mgr.CreateSession("test-session-4", "localhost")
	if sess == nil {
		t.Fatal("failed to create session")
	}

	// Initially empty
	if sess.GetUserID() != "" {
		t.Errorf("expected empty user ID, got '%s'", sess.GetUserID())
	}

	// Set user ID
	sess.SetUserID("testuser")
	if sess.GetUserID() != "testuser" {
		t.Errorf("expected 'testuser', got '%s'", sess.GetUserID())
	}
}

func TestSessionAdminFlag(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(5*time.Minute, logger)

	sess := mgr.CreateSession("test-session-5", "localhost")
	if sess == nil {
		t.Fatal("failed to create session")
	}

	// Initially not admin
	if sess.IsAdminSession() {
		t.Error("expected session to not be admin")
	}

	// Set admin flag
	sess.SetAdmin(true)
	if !sess.IsAdminSession() {
		t.Error("expected session to be admin")
	}
}

func TestRemoveSession(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(5*time.Minute, logger)

	sess := mgr.CreateSession("test-session-6", "localhost")
	if sess == nil {
		t.Fatal("failed to create session")
	}

	// Remove session
	mgr.RemoveSession("test-session-6")

	// Should no longer be valid
	if mgr.ValidateSession("test-session-6") {
		t.Error("expected removed session to be invalid")
	}
}
