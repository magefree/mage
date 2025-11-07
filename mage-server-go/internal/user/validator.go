package user

import (
	"fmt"
	"regexp"

	"github.com/magefree/mage-server-go/internal/config"
)

// Validator validates user input
type Validator struct {
	cfg config.ValidationConfig
}

// NewValidator creates a new validator
func NewValidator(cfg config.ValidationConfig) *Validator {
	return &Validator{cfg: cfg}
}

// ValidateUsername validates a username
func (v *Validator) ValidateUsername(username string) error {
	if len(username) < v.cfg.Username.MinLength {
		return fmt.Errorf("username must be at least %d characters", v.cfg.Username.MinLength)
	}

	if len(username) > v.cfg.Username.MaxLength {
		return fmt.Errorf("username must be at most %d characters", v.cfg.Username.MaxLength)
	}

	// Check pattern
	if v.cfg.Username.Pattern != "" {
		matched, err := regexp.MatchString(v.cfg.Username.Pattern, username)
		if err != nil {
			return fmt.Errorf("invalid username pattern: %w", err)
		}
		if !matched {
			return fmt.Errorf("username must match pattern: %s", v.cfg.Username.Pattern)
		}
	}

	return nil
}

// ValidatePassword validates a password
func (v *Validator) ValidatePassword(password string) error {
	if len(password) < v.cfg.Password.MinLength {
		return fmt.Errorf("password must be at least %d characters", v.cfg.Password.MinLength)
	}

	if len(password) > v.cfg.Password.MaxLength {
		return fmt.Errorf("password must be at most %d characters", v.cfg.Password.MaxLength)
	}

	return nil
}

// ValidateEmail validates an email address
func (v *Validator) ValidateEmail(email string) error {
	if email == "" {
		return nil // Email is optional
	}

	// Simple email validation
	emailRegex := regexp.MustCompile(`^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$`)
	if !emailRegex.MatchString(email) {
		return fmt.Errorf("invalid email address")
	}

	return nil
}
