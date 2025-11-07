package repository

import (
	"context"
	"fmt"

	"github.com/jackc/pgx/v5"
)

// UserRepository handles user database operations
type UserRepository struct {
	db *DB
}

// NewUserRepository creates a new user repository
func NewUserRepository(db *DB) *UserRepository {
	return &UserRepository{db: db}
}

// Create creates a new user
func (r *UserRepository) Create(ctx context.Context, u *User) error {
	query := `
		INSERT INTO authorized_users (name, password, email, active)
		VALUES ($1, $2, $3, $4)
		RETURNING id, created_at, updated_at
	`

	err := r.db.Pool.QueryRow(ctx, query, u.Name, u.Password, u.Email, u.Active).
		Scan(&u.ID, &u.CreatedAt, &u.UpdatedAt)
	if err != nil {
		return fmt.Errorf("failed to create user: %w", err)
	}

	return nil
}

// GetByName retrieves a user by username
func (r *UserRepository) GetByName(ctx context.Context, name string) (*User, error) {
	query := `
		SELECT id, name, password, email, active, created_at, updated_at,
		       lock_end_time, chat_lock_end_time
		FROM authorized_users
		WHERE name = $1
	`

	u := &User{}
	err := r.db.Pool.QueryRow(ctx, query, name).Scan(
		&u.ID, &u.Name, &u.Password, &u.Email, &u.Active,
		&u.CreatedAt, &u.UpdatedAt, &u.LockEndTime, &u.ChatLockEndTime,
	)
	if err != nil {
		if err == pgx.ErrNoRows {
			return nil, fmt.Errorf("user not found: %s", name)
		}
		return nil, fmt.Errorf("failed to get user: %w", err)
	}

	return u, nil
}

// GetByEmail retrieves a user by email
func (r *UserRepository) GetByEmail(ctx context.Context, email string) (*User, error) {
	query := `
		SELECT id, name, password, email, active, created_at, updated_at,
		       lock_end_time, chat_lock_end_time
		FROM authorized_users
		WHERE email = $1
	`

	u := &User{}
	err := r.db.Pool.QueryRow(ctx, query, email).Scan(
		&u.ID, &u.Name, &u.Password, &u.Email, &u.Active,
		&u.CreatedAt, &u.UpdatedAt, &u.LockEndTime, &u.ChatLockEndTime,
	)
	if err != nil {
		if err == pgx.ErrNoRows {
			return nil, fmt.Errorf("user not found with email: %s", email)
		}
		return nil, fmt.Errorf("failed to get user: %w", err)
	}

	return u, nil
}

// Update updates a user
func (r *UserRepository) Update(ctx context.Context, u *User) error {
	query := `
		UPDATE authorized_users
		SET password = $1, email = $2, active = $3,
		    lock_end_time = $4, chat_lock_end_time = $5
		WHERE id = $6
	`

	_, err := r.db.Pool.Exec(ctx, query,
		u.Password, u.Email, u.Active, u.LockEndTime, u.ChatLockEndTime, u.ID)
	if err != nil {
		return fmt.Errorf("failed to update user: %w", err)
	}

	return nil
}

// UpdatePassword updates a user's password
func (r *UserRepository) UpdatePassword(ctx context.Context, name, passwordHash string) error {
	query := `
		UPDATE authorized_users
		SET password = $1
		WHERE name = $2
	`

	_, err := r.db.Pool.Exec(ctx, query, passwordHash, name)
	if err != nil {
		return fmt.Errorf("failed to update password: %w", err)
	}

	return nil
}

// Exists checks if a user exists by username
func (r *UserRepository) Exists(ctx context.Context, name string) (bool, error) {
	query := `SELECT EXISTS(SELECT 1 FROM authorized_users WHERE name = $1)`

	var exists bool
	err := r.db.Pool.QueryRow(ctx, query, name).Scan(&exists)
	if err != nil {
		return false, fmt.Errorf("failed to check user existence: %w", err)
	}

	return exists, nil
}

// GetAll retrieves all users
func (r *UserRepository) GetAll(ctx context.Context) ([]*User, error) {
	query := `
		SELECT id, name, password, email, active, created_at, updated_at,
		       lock_end_time, chat_lock_end_time
		FROM authorized_users
		ORDER BY name
	`

	rows, err := r.db.Pool.Query(ctx, query)
	if err != nil {
		return nil, fmt.Errorf("failed to get all users: %w", err)
	}
	defer rows.Close()

	users := make([]*User, 0)
	for rows.Next() {
		u := &User{}
		err := rows.Scan(
			&u.ID, &u.Name, &u.Password, &u.Email, &u.Active,
			&u.CreatedAt, &u.UpdatedAt, &u.LockEndTime, &u.ChatLockEndTime,
		)
		if err != nil {
			return nil, fmt.Errorf("failed to scan user: %w", err)
		}
		users = append(users, u)
	}

	return users, nil
}
