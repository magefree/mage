-- Drop trigger
DROP TRIGGER IF EXISTS update_authorized_users_updated_at ON authorized_users;

-- Drop function
DROP FUNCTION IF EXISTS update_updated_at_column();

-- Drop indexes
DROP INDEX IF EXISTS idx_authorized_users_email;
DROP INDEX IF EXISTS idx_authorized_users_name;

-- Drop table
DROP TABLE IF EXISTS authorized_users;
