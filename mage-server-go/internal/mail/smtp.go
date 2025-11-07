package mail

import (
	"fmt"
	"net/smtp"

	"github.com/magefree/mage-server-go/internal/config"
	"go.uber.org/zap"
)

// SMTPClient implements email sending via SMTP
type SMTPClient struct {
	config config.SMTPConfig
	logger *zap.Logger
}

// NewSMTPClient creates a new SMTP email client
func NewSMTPClient(cfg config.SMTPConfig, logger *zap.Logger) (*SMTPClient, error) {
	if cfg.Host == "" {
		return nil, fmt.Errorf("SMTP host is required")
	}

	return &SMTPClient{
		config: cfg,
		logger: logger,
	}, nil
}

// SendPasswordResetEmail sends a password reset email
func (c *SMTPClient) SendPasswordResetEmail(to, username, token string) error {
	subject := "MAGE - Password Reset"
	body := fmt.Sprintf(`
Hello %s,

You have requested to reset your password.

Your password reset code is: %s

This code will expire in 1 hour.

If you did not request this, please ignore this email.

Best regards,
MAGE Server Team
`, username, token)

	return c.SendEmail(to, subject, body)
}

// SendWelcomeEmail sends a welcome email
func (c *SMTPClient) SendWelcomeEmail(to, username string) error {
	subject := "Welcome to MAGE"
	body := fmt.Sprintf(`
Hello %s,

Welcome to MAGE!

Your account has been successfully created.

You can now log in and start playing.

Best regards,
MAGE Server Team
`, username)

	return c.SendEmail(to, subject, body)
}

// SendEmail sends an email via SMTP
func (c *SMTPClient) SendEmail(to, subject, body string) error {
	// Construct message
	msg := fmt.Sprintf("From: %s\r\n"+
		"To: %s\r\n"+
		"Subject: %s\r\n"+
		"\r\n"+
		"%s\r\n", c.config.From, to, subject, body)

	// Connect to SMTP server
	addr := fmt.Sprintf("%s:%d", c.config.Host, c.config.Port)

	// Setup authentication
	var auth smtp.Auth
	if c.config.User != "" && c.config.Password != "" {
		auth = smtp.PlainAuth("", c.config.User, c.config.Password, c.config.Host)
	}

	// Send email
	err := smtp.SendMail(addr, auth, c.config.From, []string{to}, []byte(msg))
	if err != nil {
		c.logger.Error("failed to send email",
			zap.String("to", to),
			zap.String("subject", subject),
			zap.Error(err),
		)
		return fmt.Errorf("failed to send email: %w", err)
	}

	c.logger.Info("email sent",
		zap.String("to", to),
		zap.String("subject", subject),
	)

	return nil
}
