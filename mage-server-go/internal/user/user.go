package user

import (
	"time"
)

// User represents a user account
type User struct {
	ID               int64
	Name             string
	Password         string // Argon2id hash
	Email            string
	Active           bool
	CreatedAt        time.Time
	UpdatedAt        time.Time
	LockEndTime      *time.Time
	ChatLockEndTime  *time.Time
}

// IsLocked checks if the user account is locked
func (u *User) IsLocked() bool {
	if u.LockEndTime == nil {
		return false
	}
	return time.Now().Before(*u.LockEndTime)
}

// IsChatLocked checks if the user is chat-locked
func (u *User) IsChatLocked() bool {
	if u.ChatLockEndTime == nil {
		return false
	}
	return time.Now().Before(*u.ChatLockEndTime)
}

// UserStats represents user statistics
type UserStats struct {
	ID              int64
	UserName        string
	Matches         int
	Tournaments     int
	TourneysWon     int
	TourneysSecond  int
	Wins            int
	Losses          int
	Draws           int
	QuitRatio       float64
	Rating          float64
	RatingDeviation float64
	Volatility      float64
	CreatedAt       time.Time
	UpdatedAt       time.Time
}
