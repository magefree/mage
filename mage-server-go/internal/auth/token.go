package auth

import (
	"crypto/rand"
	"fmt"
	"math/big"
	"sync"
	"time"
)

// TokenStore stores password reset tokens
type TokenStore struct {
	tokens map[string]*Token // email -> token
	mu     sync.RWMutex
	ttl    time.Duration
}

// Token represents a password reset token
type Token struct {
	Value     string
	ExpiresAt time.Time
}

// NewTokenStore creates a new token store
func NewTokenStore(ttl time.Duration) *TokenStore {
	return &TokenStore{
		tokens: make(map[string]*Token),
		ttl:    ttl,
	}
}

// GenerateToken generates a 6-digit password reset token
func (ts *TokenStore) GenerateToken(email string) (string, error) {
	// Generate 6-digit token
	token, err := generate6DigitToken()
	if err != nil {
		return "", err
	}

	ts.mu.Lock()
	defer ts.mu.Unlock()

	ts.tokens[email] = &Token{
		Value:     token,
		ExpiresAt: time.Now().Add(ts.ttl),
	}

	return token, nil
}

// VerifyToken verifies a password reset token
func (ts *TokenStore) VerifyToken(email, token string) bool {
	ts.mu.RLock()
	defer ts.mu.RUnlock()

	t, ok := ts.tokens[email]
	if !ok {
		return false
	}

	// Check expiration
	if time.Now().After(t.ExpiresAt) {
		return false
	}

	// Check token value
	return t.Value == token
}

// ConsumeToken verifies and removes a token (one-time use)
func (ts *TokenStore) ConsumeToken(email, token string) bool {
	ts.mu.Lock()
	defer ts.mu.Unlock()

	t, ok := ts.tokens[email]
	if !ok {
		return false
	}

	// Check expiration
	if time.Now().After(t.ExpiresAt) {
		delete(ts.tokens, email)
		return false
	}

	// Check token value
	if t.Value != token {
		return false
	}

	// Remove token (one-time use)
	delete(ts.tokens, email)
	return true
}

// CleanupExpired removes expired tokens
func (ts *TokenStore) CleanupExpired() {
	ts.mu.Lock()
	defer ts.mu.Unlock()

	now := time.Now()
	for email, token := range ts.tokens {
		if now.After(token.ExpiresAt) {
			delete(ts.tokens, email)
		}
	}
}

// generate6DigitToken generates a secure 6-digit token
func generate6DigitToken() (string, error) {
	// Generate random number between 100000 and 999999
	n, err := rand.Int(rand.Reader, big.NewInt(900000))
	if err != nil {
		return "", fmt.Errorf("failed to generate token: %w", err)
	}

	return fmt.Sprintf("%06d", n.Int64()+100000), nil
}
