package repository

import (
	"context"
	"fmt"
	"time"

	"github.com/jackc/pgx/v5/pgxpool"
	"github.com/magefree/mage-server-go/internal/config"
	"go.uber.org/zap"
)

// DB wraps the PostgreSQL connection pool
type DB struct {
	Pool   *pgxpool.Pool
	logger *zap.Logger
}

// NewDB creates a new database connection pool
func NewDB(ctx context.Context, cfg config.DatabaseConfig, logger *zap.Logger) (*DB, error) {
	// Construct DSN
	dsn := fmt.Sprintf("postgres://%s:%s@%s:%d/%s?sslmode=%s",
		cfg.User,
		cfg.Password,
		cfg.Host,
		cfg.Port,
		cfg.Database,
		cfg.SSLMode,
	)

	// Parse pool config
	poolConfig, err := pgxpool.ParseConfig(dsn)
	if err != nil {
		return nil, fmt.Errorf("failed to parse database config: %w", err)
	}

	// Configure connection pool
	poolConfig.MaxConns = int32(cfg.MaxOpenConns)
	poolConfig.MinConns = int32(cfg.MaxIdleConns)
	poolConfig.MaxConnLifetime = cfg.ConnMaxLifetime
	poolConfig.MaxConnIdleTime = 30 * time.Minute
	poolConfig.HealthCheckPeriod = 1 * time.Minute

	// Create connection pool
	pool, err := pgxpool.NewWithConfig(ctx, poolConfig)
	if err != nil {
		return nil, fmt.Errorf("failed to create connection pool: %w", err)
	}

	// Ping database to verify connection
	if err := pool.Ping(ctx); err != nil {
		pool.Close()
		return nil, fmt.Errorf("failed to ping database: %w", err)
	}

	logger.Info("database connection established",
		zap.String("host", cfg.Host),
		zap.Int("port", cfg.Port),
		zap.String("database", cfg.Database),
		zap.Int("max_conns", cfg.MaxOpenConns),
	)

	return &DB{
		Pool:   pool,
		logger: logger,
	}, nil
}

// Close closes the database connection pool
func (db *DB) Close() {
	db.logger.Info("closing database connection pool")
	db.Pool.Close()
}

// Health checks the database connection health
func (db *DB) Health(ctx context.Context) error {
	return db.Pool.Ping(ctx)
}

// Stats returns connection pool statistics
func (db *DB) Stats() *pgxpool.Stat {
	return db.Pool.Stat()
}
