package integration

import (
	"testing"

	"github.com/magefree/mage-server-go/internal/tournament"
	"go.uber.org/zap/zaptest"
)

// TestCompleteTournamentFlow tests a complete tournament from creation to completion
func TestCompleteTournamentFlow(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := tournament.NewManager(logger)

	// Create tournament
	tourney := mgr.CreateTournament("Test Tournament", "Constructed", "admin", "main", 3, 2)
	if tourney == nil {
		t.Fatal("failed to create tournament")
	}

	// Add 4 players
	players := []string{"Alice", "Bob", "Charlie", "Dave"}
	for _, player := range players {
		if err := tourney.AddPlayer(player); err != nil {
			t.Fatalf("failed to add player %s: %v", player, err)
		}
	}

	// Verify player count
	if tourney.GetPlayerCount() != 4 {
		t.Errorf("expected 4 players, got %d", tourney.GetPlayerCount())
	}

	// Start round 1
	round1 := tourney.CreateRound()
	if round1 == nil {
		t.Fatal("failed to create round 1")
	}

	// Verify pairings
	if len(round1.Pairings) != 2 {
		t.Errorf("expected 2 pairings in round 1, got %d", len(round1.Pairings))
	}

	// Record results for round 1
	for i, pairing := range round1.Pairings {
		// Player 1 wins all matches
		err := tourney.RecordMatchResult(1, pairing.Player1, pairing.Player2, pairing.Player1, 2, 0)
		if err != nil {
			t.Fatalf("failed to record match %d result: %v", i, err)
		}
	}

	// Verify standings after round 1
	for _, player := range tourney.GetPlayers() {
		if player.Name == round1.Pairings[0].Player1 || player.Name == round1.Pairings[1].Player1 {
			if player.Points != 3 {
				t.Errorf("winner %s should have 3 points, got %d", player.Name, player.Points)
			}
			if player.Wins != 1 {
				t.Errorf("winner %s should have 1 win, got %d", player.Name, player.Wins)
			}
		} else {
			if player.Points != 0 {
				t.Errorf("loser %s should have 0 points, got %d", player.Name, player.Points)
			}
			if player.Losses != 1 {
				t.Errorf("loser %s should have 1 loss, got %d", player.Name, player.Losses)
			}
		}
	}

	// Start round 2
	round2 := tourney.CreateRound()
	if round2 == nil {
		t.Fatal("failed to create round 2")
	}

	// Swiss pairing should pair winners vs winners, losers vs losers
	// In our simple implementation, it just pairs sequentially
	if len(round2.Pairings) != 2 {
		t.Errorf("expected 2 pairings in round 2, got %d", len(round2.Pairings))
	}

	// Record results for round 2
	for _, pairing := range round2.Pairings {
		err := tourney.RecordMatchResult(2, pairing.Player1, pairing.Player2, pairing.Player1, 2, 1)
		if err != nil {
			t.Fatalf("failed to record round 2 result: %v", err)
		}
	}

	// Verify current round number
	if tourney.CurrentRound != 2 {
		t.Errorf("expected current round 2, got %d", tourney.CurrentRound)
	}

	// Verify total rounds
	if len(tourney.Rounds) != 2 {
		t.Errorf("expected 2 total rounds, got %d", len(tourney.Rounds))
	}
}

// TestOddPlayerTournament tests tournament with odd number of players (bye handling)
func TestOddPlayerTournament(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := tournament.NewManager(logger)

	tourney := mgr.CreateTournament("Odd Player Tournament", "Constructed", "admin", "main", 2, 2)

	// Add 5 players (odd number)
	players := []string{"Alice", "Bob", "Charlie", "Dave", "Eve"}
	for _, player := range players {
		tourney.AddPlayer(player)
	}

	// Create round 1
	round := tourney.CreateRound()

	// Should have 2 pairings (4 players) and 1 bye
	if len(round.Pairings) != 2 {
		t.Errorf("expected 2 pairings with 5 players, got %d", len(round.Pairings))
	}

	// One player should have received a bye (3 points, 1 win)
	byeCount := 0
	for _, player := range tourney.GetPlayers() {
		if player.Points == 3 && player.Wins == 1 {
			byeCount++
			t.Logf("Player %s received bye", player.Name)
		}
	}

	if byeCount != 1 {
		t.Errorf("expected exactly 1 player with bye, got %d", byeCount)
	}

	// Count paired players
	pairedPlayers := make(map[string]bool)
	for _, pairing := range round.Pairings {
		pairedPlayers[pairing.Player1] = true
		pairedPlayers[pairing.Player2] = true
	}

	if len(pairedPlayers) != 4 {
		t.Errorf("expected 4 paired players, got %d", len(pairedPlayers))
	}
}

// TestTournamentRemoval tests tournament cleanup
func TestTournamentRemoval(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := tournament.NewManager(logger)

	// Create tournament
	tourney := mgr.CreateTournament("Temp Tournament", "Sealed", "admin", "main", 3, 2)
	tourneyID := tourney.ID

	// Verify it exists
	retrieved, ok := mgr.GetTournament(tourneyID)
	if !ok {
		t.Fatal("tournament should exist after creation")
	}
	if retrieved.ID != tourneyID {
		t.Error("retrieved tournament ID mismatch")
	}

	// Remove tournament
	mgr.RemoveTournament(tourneyID)

	// Verify it's gone
	_, ok = mgr.GetTournament(tourneyID)
	if ok {
		t.Error("tournament should not exist after removal")
	}
}

// TestMultipleTournaments tests managing multiple concurrent tournaments
func TestMultipleTournaments(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := tournament.NewManager(logger)

	// Create 3 tournaments
	t1 := mgr.CreateTournament("Tournament 1", "Constructed", "admin1", "room1", 3, 2)
	t2 := mgr.CreateTournament("Tournament 2", "Sealed", "admin2", "room2", 5, 2)
	t3 := mgr.CreateTournament("Tournament 3", "Draft", "admin3", "room3", 4, 2)

	// Verify all exist
	if _, ok := mgr.GetTournament(t1.ID); !ok {
		t.Error("tournament 1 should exist")
	}
	if _, ok := mgr.GetTournament(t2.ID); !ok {
		t.Error("tournament 2 should exist")
	}
	if _, ok := mgr.GetTournament(t3.ID); !ok {
		t.Error("tournament 3 should exist")
	}

	// Get all tournaments
	allTournaments := mgr.GetAllTournaments()
	if len(allTournaments) < 3 {
		t.Errorf("expected at least 3 tournaments, got %d", len(allTournaments))
	}

	// Set one to finished
	t1.SetState(tournament.TournamentStateFinished)

	// Check active count
	activeCount := mgr.GetActiveTournamentCount()
	if activeCount < 2 {
		t.Errorf("expected at least 2 active tournaments, got %d", activeCount)
	}
}
