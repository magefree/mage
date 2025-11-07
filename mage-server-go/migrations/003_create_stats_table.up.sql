-- Create user_stats table for tracking player statistics and ratings
CREATE TABLE IF NOT EXISTS user_stats (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    matches INTEGER DEFAULT 0,
    tournaments INTEGER DEFAULT 0,
    tourneys_won INTEGER DEFAULT 0,
    tourneys_second INTEGER DEFAULT 0,
    wins INTEGER DEFAULT 0,
    losses INTEGER DEFAULT 0,
    draws INTEGER DEFAULT 0,
    quit_ratio NUMERIC(5,2) DEFAULT 0.0,

    -- Glicko-2 rating system fields
    rating NUMERIC(10,2) DEFAULT 1500.0,
    rating_deviation NUMERIC(10,2) DEFAULT 350.0,
    volatility NUMERIC(10,4) DEFAULT 0.06,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraint to authorized_users
    CONSTRAINT fk_user_stats_user FOREIGN KEY (user_name)
        REFERENCES authorized_users(name) ON DELETE CASCADE
);

-- Create indexes for efficient queries
CREATE INDEX IF NOT EXISTS idx_user_stats_user_name ON user_stats(user_name);
CREATE INDEX IF NOT EXISTS idx_user_stats_rating ON user_stats(rating DESC);
CREATE INDEX IF NOT EXISTS idx_user_stats_wins ON user_stats(wins DESC);
CREATE INDEX IF NOT EXISTS idx_user_stats_tournaments ON user_stats(tournaments DESC);

-- Trigger to update updated_at timestamp
CREATE TRIGGER update_user_stats_updated_at BEFORE UPDATE
    ON user_stats FOR EACH ROW
    EXECUTE PROCEDURE update_updated_at_column();
