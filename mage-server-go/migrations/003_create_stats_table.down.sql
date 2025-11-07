-- Drop trigger
DROP TRIGGER IF EXISTS update_user_stats_updated_at ON user_stats;

-- Drop indexes
DROP INDEX IF EXISTS idx_user_stats_tournaments;
DROP INDEX IF EXISTS idx_user_stats_wins;
DROP INDEX IF EXISTS idx_user_stats_rating;
DROP INDEX IF EXISTS idx_user_stats_user_name;

-- Drop table
DROP TABLE IF EXISTS user_stats;
