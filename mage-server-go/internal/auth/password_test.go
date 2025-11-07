package auth

import (
	"strings"
	"testing"
)

func TestHashPassword(t *testing.T) {
	password := "MySecurePassword123!"

	hash, err := HashPassword(password)
	if err != nil {
		t.Fatalf("failed to hash password: %v", err)
	}

	// Check format: $argon2id$v=19$m=65536,t=1,p=4$<salt>$<hash>
	if !strings.HasPrefix(hash, "$argon2id$v=19$") {
		t.Errorf("invalid hash format: %s", hash)
	}

	// Hash should be different each time (due to random salt)
	hash2, err := HashPassword(password)
	if err != nil {
		t.Fatalf("failed to hash password second time: %v", err)
	}

	if hash == hash2 {
		t.Error("expected different hashes due to different salts")
	}
}

func TestVerifyPassword(t *testing.T) {
	password := "MySecurePassword123!"

	hash, err := HashPassword(password)
	if err != nil {
		t.Fatalf("failed to hash password: %v", err)
	}

	// Correct password should verify
	if !VerifyPassword(password, hash) {
		t.Error("expected password to verify correctly")
	}

	// Incorrect password should not verify
	if VerifyPassword("WrongPassword", hash) {
		t.Error("expected incorrect password to fail verification")
	}
}

func TestVerifyPasswordInvalidHash(t *testing.T) {
	// Invalid hash format should return false
	if VerifyPassword("password", "invalid-hash") {
		t.Error("expected invalid hash to fail verification")
	}

	// Malformed argon2id hash
	if VerifyPassword("password", "$argon2id$v=19$m=65536,t=1,p=4$invalidsalt$invalidhash") {
		t.Error("expected malformed hash to fail verification")
	}
}

func TestPasswordHashSecurity(t *testing.T) {
	password := "TestPassword123"

	// Hash should use sufficient parameters
	hash, err := HashPassword(password)
	if err != nil {
		t.Fatalf("failed to hash password: %v", err)
	}

	// Check memory parameter (should be 64MB = 65536 KB)
	if !strings.Contains(hash, "m=65536") {
		t.Error("expected memory parameter of 65536 KB")
	}

	// Check parallelism (should be 4 threads)
	if !strings.Contains(hash, "p=4") {
		t.Error("expected parallelism of 4")
	}
}
