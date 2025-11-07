package tournament

import (
	"testing"

	"go.uber.org/zap/zaptest"
)

func TestCreateTournament(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(logger)

	tournament := mgr.CreateTournament("Test Tournament", "Constructed", "controller1", "room1", 3, 2)
	if tournament == nil {
		t.Fatal("expected tournament to be created")
	}

	if tournament.Type != TournamentTypeSwiss {
		t.Errorf("expected Swiss tournament, got %s", tournament.Type)
	}

	if tournament.State != TournamentStateWaiting {
		t.Errorf("expected WAITING state, got %s", tournament.State.String())
	}
}

func TestAddPlayer(t *testing.T) {
	tournament := NewTournament("Test", "Constructed", "controller1", "room1", 3, 2)

	err := tournament.AddPlayer("Alice")
	if err != nil {
		t.Fatalf("failed to add player: %v", err)
	}

	if len(tournament.Players) != 1 {
		t.Errorf("expected 1 player, got %d", len(tournament.Players))
	}

	// Cannot add duplicate
	err = tournament.AddPlayer("Alice")
	if err == nil {
		t.Error("expected error when adding duplicate player")
	}
}

func TestCreateRound(t *testing.T) {
	tournament := NewTournament("Test", "Constructed", "controller1", "room1", 3, 2)

	// Add players
	tournament.AddPlayer("Alice")
	tournament.AddPlayer("Bob")
	tournament.AddPlayer("Charlie")
	tournament.AddPlayer("Dave")

	// Create first round
	round := tournament.CreateRound()
	if round == nil {
		t.Fatal("expected round to be created")
	}

	if tournament.CurrentRound != 1 {
		t.Errorf("expected current round 1, got %d", tournament.CurrentRound)
	}

	// Should have created pairings
	if len(tournament.Rounds) != 1 {
		t.Errorf("expected 1 round, got %d", len(tournament.Rounds))
	}
}

func TestSwissPairing(t *testing.T) {
	tournament := NewTournament("Test", "Constructed", "controller1", "room1", 3, 2)

	// Add 4 players
	tournament.AddPlayer("Alice")
	tournament.AddPlayer("Bob")
	tournament.AddPlayer("Charlie")
	tournament.AddPlayer("Dave")

	tournament.CreateRound()

	// Check first round pairings
	round := tournament.Rounds[0]
	if len(round.Pairings) != 2 {
		t.Errorf("expected 2 pairings, got %d", len(round.Pairings))
	}

	// All players should be paired
	pairedPlayers := make(map[string]bool)
	for _, pairing := range round.Pairings {
		pairedPlayers[pairing.Player1] = true
		pairedPlayers[pairing.Player2] = true
	}

	if len(pairedPlayers) != 4 {
		t.Errorf("expected all 4 players to be paired, got %d", len(pairedPlayers))
	}
}

func TestOddPlayerBye(t *testing.T) {
	tournament := NewTournament("Test", "Constructed", "controller1", "room1", 3, 2)

	// Add 3 players (odd number)
	tournament.AddPlayer("Alice")
	tournament.AddPlayer("Bob")
	tournament.AddPlayer("Charlie")

	tournament.CreateRound()

	round := tournament.Rounds[0]

	// Should have 1 pairing
	if len(round.Pairings) != 1 {
		t.Errorf("expected 1 pairing, got %d", len(round.Pairings))
	}

	// One player should have received a bye (3 points)
	byeCount := 0
	for _, player := range tournament.Players {
		if player.Points == 3 {
			byeCount++
		}
	}

	if byeCount != 1 {
		t.Errorf("expected exactly 1 player with bye, got %d", byeCount)
	}
}

func TestRecordMatchResult(t *testing.T) {
	tournament := NewTournament("Test", "Constructed", "controller1", "room1", 3, 2)

	tournament.AddPlayer("Alice")
	tournament.AddPlayer("Bob")
	tournament.CreateRound()

	round := tournament.Rounds[0]
	pairing := round.Pairings[0]

	// Record win for Player1
	err := tournament.RecordMatchResult(1, pairing.Player1, pairing.Player2, pairing.Player1, 2, 0)
	if err != nil {
		t.Fatalf("failed to record match result: %v", err)
	}

	// Winner should have 3 points
	winner := tournament.Players[pairing.Player1]
	if winner.Points != 3 {
		t.Errorf("expected winner to have 3 points, got %d", winner.Points)
	}

	if winner.Wins != 1 {
		t.Errorf("expected winner to have 1 win, got %d", winner.Wins)
	}

	// Loser should have 0 points
	loser := tournament.Players[pairing.Player2]
	if loser.Points != 0 {
		t.Errorf("expected loser to have 0 points, got %d", loser.Points)
	}

	if loser.Losses != 1 {
		t.Errorf("expected loser to have 1 loss, got %d", loser.Losses)
	}
}

func TestMultipleRounds(t *testing.T) {
	tournament := NewTournament("Test", "Constructed", "controller1", "room1", 3, 2)

	// Add 4 players
	for _, name := range []string{"Alice", "Bob", "Charlie", "Dave"} {
		tournament.AddPlayer(name)
	}
	tournament.CreateRound()

	// Complete first round
	for _, pairing := range tournament.Rounds[0].Pairings {
		tournament.RecordMatchResult(1, pairing.Player1, pairing.Player2, pairing.Player1, 2, 0)
	}

	// Create second round
	tournament.CreateRound()

	if len(tournament.Rounds) != 2 {
		t.Errorf("expected 2 rounds, got %d", len(tournament.Rounds))
	}

	if tournament.CurrentRound != 2 {
		t.Errorf("expected current round 2, got %d", tournament.CurrentRound)
	}
}

func TestGetTournament(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(logger)

	tournament := mgr.CreateTournament("Test", "Constructed", "controller1", "room1", 3, 2)
	tournamentID := tournament.ID

	retrieved, ok := mgr.GetTournament(tournamentID)
	if !ok {
		t.Fatal("failed to retrieve tournament")
	}

	if retrieved.ID != tournamentID {
		t.Errorf("expected tournament ID '%s', got '%s'", tournamentID, retrieved.ID)
	}
}

func TestRemoveTournament(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(logger)

	tournament := mgr.CreateTournament("Test", "Constructed", "controller1", "room1", 3, 2)
	tournamentID := tournament.ID

	mgr.RemoveTournament(tournamentID)

	_, ok := mgr.GetTournament(tournamentID)
	if ok {
		t.Error("expected tournament to be removed")
	}
}
