package mail

import (
	"fmt"

	"github.com/magefree/mage-server-go/internal/config"
	"go.uber.org/zap"
)

// Client is the interface for email clients
type Client interface {
	SendPasswordResetEmail(to, username, token string) error
	SendWelcomeEmail(to, username string) error
	SendEmail(to, subject, body string) error
}

// NewClient creates a new email client based on configuration
func NewClient(cfg config.MailConfig, logger *zap.Logger) (Client, error) {
	switch cfg.Provider {
	case "smtp":
		return NewSMTPClient(cfg.SMTP, logger)
	case "mailgun":
		return NewMailgunClient(cfg.Mailgun, logger)
	case "":
		return &noOpClient{logger: logger}, nil
	default:
		return nil, fmt.Errorf("unknown mail provider: %s", cfg.Provider)
	}
}

// noOpClient is a no-op email client for when email is disabled
type noOpClient struct {
	logger *zap.Logger
}

func (c *noOpClient) SendPasswordResetEmail(to, username, token string) error {
	c.logger.Info("email disabled - would send password reset",
		zap.String("to", to),
		zap.String("username", username),
		zap.String("token", token),
	)
	return nil
}

func (c *noOpClient) SendWelcomeEmail(to, username string) error {
	c.logger.Info("email disabled - would send welcome email",
		zap.String("to", to),
		zap.String("username", username),
	)
	return nil
}

func (c *noOpClient) SendEmail(to, subject, body string) error {
	c.logger.Info("email disabled - would send email",
		zap.String("to", to),
		zap.String("subject", subject),
	)
	return nil
}
