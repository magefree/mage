-- Drop indexes
DROP INDEX IF EXISTS idx_table_records_created_at;
DROP INDEX IF EXISTS idx_table_records_tournament_type;
DROP INDEX IF EXISTS idx_table_records_game_type;
DROP INDEX IF EXISTS idx_table_records_start_time;
DROP INDEX IF EXISTS idx_table_records_table_id;

-- Drop table
DROP TABLE IF EXISTS table_records;
