package config

import (
	"fmt"
	"time"

	"github.com/spf13/viper"
)

// Config represents the complete server configuration
type Config struct {
	Server     ServerConfig     `mapstructure:"server"`
	Database   DatabaseConfig   `mapstructure:"database"`
	Validation ValidationConfig `mapstructure:"validation"`
	Auth       AuthConfig       `mapstructure:"auth"`
	Mail       MailConfig       `mapstructure:"mail"`
	Logging    LoggingConfig    `mapstructure:"logging"`
	Cache      CacheConfig      `mapstructure:"cache"`
	Plugins    PluginConfig     `mapstructure:"plugins"`
	Metrics    MetricsConfig    `mapstructure:"metrics"`
}

// ServerConfig contains server-related settings
type ServerConfig struct {
	Name           string          `mapstructure:"name"`
	GRPC           GRPCConfig      `mapstructure:"grpc"`
	WebSocket      WebSocketConfig `mapstructure:"websocket"`
	MaxSessions    int             `mapstructure:"max_sessions"`
	LeasePeriod    time.Duration   `mapstructure:"lease_period"`
	MaxIdleSeconds int             `mapstructure:"max_idle_seconds"`
	MaxGameThreads int             `mapstructure:"max_game_threads"`
}

// GRPCConfig contains gRPC server settings
type GRPCConfig struct {
	Address              string        `mapstructure:"address"`
	MaxConcurrentStreams int           `mapstructure:"max_concurrent_streams"`
	MaxConnectionAge     time.Duration `mapstructure:"max_connection_age"`
}

// WebSocketConfig contains WebSocket server settings
type WebSocketConfig struct {
	Address      string        `mapstructure:"address"`
	PingInterval time.Duration `mapstructure:"ping_interval"`
	PongTimeout  time.Duration `mapstructure:"pong_timeout"`
}

// DatabaseConfig contains database connection settings
type DatabaseConfig struct {
	Host            string        `mapstructure:"host"`
	Port            int           `mapstructure:"port"`
	Database        string        `mapstructure:"database"`
	User            string        `mapstructure:"user"`
	Password        string        `mapstructure:"password"`
	SSLMode         string        `mapstructure:"sslmode"`
	MaxOpenConns    int           `mapstructure:"max_open_conns"`
	MaxIdleConns    int           `mapstructure:"max_idle_conns"`
	ConnMaxLifetime time.Duration `mapstructure:"conn_max_lifetime"`
}

// ValidationConfig contains validation rules
type ValidationConfig struct {
	Username UsernameValidation `mapstructure:"username"`
	Password PasswordValidation `mapstructure:"password"`
}

// UsernameValidation contains username validation rules
type UsernameValidation struct {
	MinLength int    `mapstructure:"min_length"`
	MaxLength int    `mapstructure:"max_length"`
	Pattern   string `mapstructure:"pattern"`
}

// PasswordValidation contains password validation rules
type PasswordValidation struct {
	MinLength int `mapstructure:"min_length"`
	MaxLength int `mapstructure:"max_length"`
}

// AuthConfig contains authentication settings
type AuthConfig struct {
	Mode                   string        `mapstructure:"mode"`
	RequireEmail           bool          `mapstructure:"require_email"`
	PasswordResetTokenTTL  time.Duration `mapstructure:"password_reset_token_ttl"`
}

// MailConfig contains email service settings
type MailConfig struct {
	Provider string        `mapstructure:"provider"`
	SMTP     SMTPConfig    `mapstructure:"smtp"`
	Mailgun  MailgunConfig `mapstructure:"mailgun"`
}

// SMTPConfig contains SMTP server settings
type SMTPConfig struct {
	Host     string `mapstructure:"host"`
	Port     int    `mapstructure:"port"`
	User     string `mapstructure:"user"`
	Password string `mapstructure:"password"`
	From     string `mapstructure:"from"`
}

// MailgunConfig contains Mailgun API settings
type MailgunConfig struct {
	Domain string `mapstructure:"domain"`
	APIKey string `mapstructure:"api_key"`
	From   string `mapstructure:"from"`
}

// LoggingConfig contains logging settings
type LoggingConfig struct {
	Level  string `mapstructure:"level"`
	Format string `mapstructure:"format"`
	Output string `mapstructure:"output"`
}

// CacheConfig contains caching settings
type CacheConfig struct {
	Cards CardCacheConfig `mapstructure:"cards"`
}

// CardCacheConfig contains card cache settings
type CardCacheConfig struct {
	Enabled bool          `mapstructure:"enabled"`
	TTL     time.Duration `mapstructure:"ttl"`
	MaxSize int           `mapstructure:"max_size"`
}

// PluginConfig contains plugin settings
type PluginConfig struct {
	GameTypes       []string `mapstructure:"game_types"`
	TournamentTypes []string `mapstructure:"tournament_types"`
	PlayerTypes     []string `mapstructure:"player_types"`
}

// MetricsConfig contains metrics settings
type MetricsConfig struct {
	Enabled bool   `mapstructure:"enabled"`
	Port    int    `mapstructure:"port"`
	Path    string `mapstructure:"path"`
}

// Load loads configuration from a file and environment variables
func Load(configPath string) (*Config, error) {
	v := viper.New()

	// Set config file path
	if configPath != "" {
		v.SetConfigFile(configPath)
	} else {
		v.SetConfigName("config")
		v.SetConfigType("yaml")
		v.AddConfigPath("./config")
		v.AddConfigPath(".")
	}

	// Enable environment variable override
	v.AutomaticEnv()

	// Set defaults
	setDefaults(v)

	// Read config file
	if err := v.ReadInConfig(); err != nil {
		return nil, fmt.Errorf("failed to read config: %w", err)
	}

	// Unmarshal config
	var cfg Config
	if err := v.Unmarshal(&cfg); err != nil {
		return nil, fmt.Errorf("failed to unmarshal config: %w", err)
	}

	// Validate config
	if err := cfg.Validate(); err != nil {
		return nil, fmt.Errorf("config validation failed: %w", err)
	}

	return &cfg, nil
}

// setDefaults sets default values for configuration
func setDefaults(v *viper.Viper) {
	// Server defaults
	v.SetDefault("server.name", "mage-server")
	v.SetDefault("server.grpc.address", "0.0.0.0:17171")
	v.SetDefault("server.grpc.max_concurrent_streams", 1000)
	v.SetDefault("server.grpc.max_connection_age", "1h")
	v.SetDefault("server.websocket.address", "0.0.0.0:17179")
	v.SetDefault("server.websocket.ping_interval", "30s")
	v.SetDefault("server.websocket.pong_timeout", "10s")
	v.SetDefault("server.max_sessions", 10000)
	v.SetDefault("server.lease_period", "5s")
	v.SetDefault("server.max_idle_seconds", 300)
	v.SetDefault("server.max_game_threads", 10)

	// Database defaults
	v.SetDefault("database.host", "localhost")
	v.SetDefault("database.port", 5432)
	v.SetDefault("database.database", "mage")
	v.SetDefault("database.user", "mage")
	v.SetDefault("database.sslmode", "disable")
	v.SetDefault("database.max_open_conns", 25)
	v.SetDefault("database.max_idle_conns", 5)
	v.SetDefault("database.conn_max_lifetime", "5m")

	// Validation defaults
	v.SetDefault("validation.username.min_length", 3)
	v.SetDefault("validation.username.max_length", 14)
	v.SetDefault("validation.username.pattern", "^[a-z0-9_]+$")
	v.SetDefault("validation.password.min_length", 8)
	v.SetDefault("validation.password.max_length", 100)

	// Auth defaults
	v.SetDefault("auth.mode", "optional")
	v.SetDefault("auth.require_email", false)
	v.SetDefault("auth.password_reset_token_ttl", "1h")

	// Logging defaults
	v.SetDefault("logging.level", "info")
	v.SetDefault("logging.format", "json")
	v.SetDefault("logging.output", "stdout")

	// Cache defaults
	v.SetDefault("cache.cards.enabled", true)
	v.SetDefault("cache.cards.ttl", "24h")
	v.SetDefault("cache.cards.max_size", 100000)

	// Metrics defaults
	v.SetDefault("metrics.enabled", true)
	v.SetDefault("metrics.port", 9090)
	v.SetDefault("metrics.path", "/metrics")
}

// Validate validates the configuration
func (c *Config) Validate() error {
	// Validate server config
	if c.Server.MaxSessions <= 0 {
		return fmt.Errorf("server.max_sessions must be positive")
	}
	if c.Server.LeasePeriod <= 0 {
		return fmt.Errorf("server.lease_period must be positive")
	}

	// Validate database config
	if c.Database.Host == "" {
		return fmt.Errorf("database.host is required")
	}
	if c.Database.Database == "" {
		return fmt.Errorf("database.database is required")
	}

	// Validate auth mode
	if c.Auth.Mode != "optional" && c.Auth.Mode != "required" {
		return fmt.Errorf("auth.mode must be 'optional' or 'required'")
	}

	// Validate mail provider
	if c.Mail.Provider != "" && c.Mail.Provider != "smtp" && c.Mail.Provider != "mailgun" {
		return fmt.Errorf("mail.provider must be 'smtp' or 'mailgun'")
	}

	// Validate logging level
	validLevels := map[string]bool{"debug": true, "info": true, "warn": true, "error": true}
	if !validLevels[c.Logging.Level] {
		return fmt.Errorf("logging.level must be one of: debug, info, warn, error")
	}

	return nil
}
