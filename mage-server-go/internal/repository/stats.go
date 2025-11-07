package repository

import (
	"context"
	"fmt"

	"github.com/jackc/pgx/v5"
	"github.com/magefree/mage-server-go/internal/user"
)

// StatsRepository handles user statistics database operations
type StatsRepository struct {
	db *DB
}

// NewStatsRepository creates a new stats repository
func NewStatsRepository(db *DB) *StatsRepository {
	return &StatsRepository{db: db}
}

// Create creates user stats entry
func (r *StatsRepository) Create(ctx context.Context, stats *user.UserStats) error {
	query := `
		INSERT INTO user_stats (user_name, matches, tournaments, tourneys_won, tourneys_second,
		                        wins, losses, draws, quit_ratio, rating, rating_deviation, volatility)
		VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12)
		RETURNING id, created_at, updated_at
	`

	err := r.db.Pool.QueryRow(ctx, query,
		stats.UserName, stats.Matches, stats.Tournaments, stats.TourneysWon, stats.TourneysSecond,
		stats.Wins, stats.Losses, stats.Draws, stats.QuitRatio,
		stats.Rating, stats.RatingDeviation, stats.Volatility,
	).Scan(&stats.ID, &stats.CreatedAt, &stats.UpdatedAt)

	if err != nil {
		return fmt.Errorf("failed to create user stats: %w", err)
	}

	return nil
}

// GetByUserName retrieves user stats by username
func (r *StatsRepository) GetByUserName(ctx context.Context, userName string) (*user.UserStats, error) {
	query := `
		SELECT id, user_name, matches, tournaments, tourneys_won, tourneys_second,
		       wins, losses, draws, quit_ratio, rating, rating_deviation, volatility,
		       created_at, updated_at
		FROM user_stats
		WHERE user_name = $1
	`

	stats := &user.UserStats{}
	err := r.db.Pool.QueryRow(ctx, query, userName).Scan(
		&stats.ID, &stats.UserName, &stats.Matches, &stats.Tournaments,
		&stats.TourneysWon, &stats.TourneysSecond, &stats.Wins, &stats.Losses, &stats.Draws,
		&stats.QuitRatio, &stats.Rating, &stats.RatingDeviation, &stats.Volatility,
		&stats.CreatedAt, &stats.UpdatedAt,
	)

	if err != nil {
		if err == pgx.ErrNoRows {
			return nil, fmt.Errorf("stats not found for user: %s", userName)
		}
		return nil, fmt.Errorf("failed to get user stats: %w", err)
	}

	return stats, nil
}

// Update updates user stats
func (r *StatsRepository) Update(ctx context.Context, stats *user.UserStats) error {
	query := `
		UPDATE user_stats
		SET matches = $1, tournaments = $2, tourneys_won = $3, tourneys_second = $4,
		    wins = $5, losses = $6, draws = $7, quit_ratio = $8,
		    rating = $9, rating_deviation = $10, volatility = $11
		WHERE user_name = $12
	`

	_, err := r.db.Pool.Exec(ctx, query,
		stats.Matches, stats.Tournaments, stats.TourneysWon, stats.TourneysSecond,
		stats.Wins, stats.Losses, stats.Draws, stats.QuitRatio,
		stats.Rating, stats.RatingDeviation, stats.Volatility,
		stats.UserName,
	)

	if err != nil {
		return fmt.Errorf("failed to update user stats: %w", err)
	}

	return nil
}

// GetTopRated retrieves top N users by rating
func (r *StatsRepository) GetTopRated(ctx context.Context, limit int) ([]*user.UserStats, error) {
	query := `
		SELECT id, user_name, matches, tournaments, tourneys_won, tourneys_second,
		       wins, losses, draws, quit_ratio, rating, rating_deviation, volatility,
		       created_at, updated_at
		FROM user_stats
		ORDER BY rating DESC
		LIMIT $1
	`

	rows, err := r.db.Pool.Query(ctx, query, limit)
	if err != nil {
		return nil, fmt.Errorf("failed to get top rated users: %w", err)
	}
	defer rows.Close()

	statsList := make([]*user.UserStats, 0)
	for rows.Next() {
		stats := &user.UserStats{}
		err := rows.Scan(
			&stats.ID, &stats.UserName, &stats.Matches, &stats.Tournaments,
			&stats.TourneysWon, &stats.TourneysSecond, &stats.Wins, &stats.Losses, &stats.Draws,
			&stats.QuitRatio, &stats.Rating, &stats.RatingDeviation, &stats.Volatility,
			&stats.CreatedAt, &stats.UpdatedAt,
		)
		if err != nil {
			return nil, fmt.Errorf("failed to scan user stats: %w", err)
		}
		statsList = append(statsList, stats)
	}

	return statsList, nil
}

// IncrementMatches increments match count for a user
func (r *StatsRepository) IncrementMatches(ctx context.Context, userName string) error {
	query := `UPDATE user_stats SET matches = matches + 1 WHERE user_name = $1`
	_, err := r.db.Pool.Exec(ctx, query, userName)
	return err
}

// RecordWin records a win for a user
func (r *StatsRepository) RecordWin(ctx context.Context, userName string) error {
	query := `UPDATE user_stats SET wins = wins + 1 WHERE user_name = $1`
	_, err := r.db.Pool.Exec(ctx, query, userName)
	return err
}

// RecordLoss records a loss for a user
func (r *StatsRepository) RecordLoss(ctx context.Context, userName string) error {
	query := `UPDATE user_stats SET losses = losses + 1 WHERE user_name = $1`
	_, err := r.db.Pool.Exec(ctx, query, userName)
	return err
}
