package mail

import (
	"fmt"

	"github.com/magefree/mage-server-go/internal/config"
	"go.uber.org/zap"
)

// MailgunClient implements email sending via Mailgun API
type MailgunClient struct {
	config config.MailgunConfig
	logger *zap.Logger
	// mailgun *mailgun.MailgunImpl // Uncomment when adding mailgun-go dependency
}

// NewMailgunClient creates a new Mailgun email client
func NewMailgunClient(cfg config.MailgunConfig, logger *zap.Logger) (*MailgunClient, error) {
	if cfg.Domain == "" || cfg.APIKey == "" {
		return nil, fmt.Errorf("Mailgun domain and API key are required")
	}

	// TODO: Initialize mailgun client when mailgun-go is added
	// mg := mailgun.NewMailgun(cfg.Domain, cfg.APIKey)

	return &MailgunClient{
		config: cfg,
		logger: logger,
	}, nil
}

// SendPasswordResetEmail sends a password reset email
func (c *MailgunClient) SendPasswordResetEmail(to, username, token string) error {
	subject := "MAGE - Password Reset"
	body := fmt.Sprintf(`
<html>
<body>
<h2>Password Reset Request</h2>
<p>Hello %s,</p>
<p>You have requested to reset your password.</p>
<p><strong>Your password reset code is: %s</strong></p>
<p>This code will expire in 1 hour.</p>
<p>If you did not request this, please ignore this email.</p>
<p>Best regards,<br>MAGE Server Team</p>
</body>
</html>
`, username, token)

	return c.SendEmail(to, subject, body)
}

// SendWelcomeEmail sends a welcome email
func (c *MailgunClient) SendWelcomeEmail(to, username string) error {
	subject := "Welcome to MAGE"
	body := fmt.Sprintf(`
<html>
<body>
<h2>Welcome to MAGE!</h2>
<p>Hello %s,</p>
<p>Your account has been successfully created.</p>
<p>You can now log in and start playing.</p>
<p>Best regards,<br>MAGE Server Team</p>
</body>
</html>
`, username)

	return c.SendEmail(to, subject, body)
}

// SendEmail sends an email via Mailgun API
func (c *MailgunClient) SendEmail(to, subject, body string) error {
	// TODO: Implement actual Mailgun API call when mailgun-go is added
	// message := c.mailgun.NewMessage(c.config.From, subject, "", to)
	// message.SetHtml(body)
	// ctx, cancel := context.WithTimeout(context.Background(), time.Second*10)
	// defer cancel()
	// _, _, err := c.mailgun.Send(ctx, message)
	// return err

	c.logger.Warn("Mailgun client not fully implemented - email not sent",
		zap.String("to", to),
		zap.String("subject", subject),
	)

	return fmt.Errorf("Mailgun client not fully implemented - add mailgun-go dependency")
}
