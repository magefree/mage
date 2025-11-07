package draft

import (
	"testing"

	"go.uber.org/zap/zaptest"
)

func TestCreateDraft(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(logger)

	players := []string{"Alice", "Bob", "Charlie"}
	draft := mgr.CreateDraft("tournament-1", "table-1", players, 3, 15)

	if draft == nil {
		t.Fatal("expected draft to be created")
	}

	if draft.TournamentID != "tournament-1" {
		t.Errorf("expected tournament ID 'tournament-1', got '%s'", draft.TournamentID)
	}

	if len(draft.Players) != 3 {
		t.Errorf("expected 3 players, got %d", len(draft.Players))
	}

	if draft.State != DraftStateWaiting {
		t.Errorf("expected state WAITING, got %s", draft.State.String())
	}
}

func TestAddPlayer(t *testing.T) {
	players := []string{"Alice"}
	draft := NewDraft("tournament-1", "table-1", players, 3, 15)

	err := draft.AddPlayer("Bob")
	if err != nil {
		t.Fatalf("failed to add player: %v", err)
	}

	if len(draft.Players) != 2 {
		t.Errorf("expected 2 players, got %d", len(draft.Players))
	}

	// Cannot add same player twice
	err = draft.AddPlayer("Bob")
	if err == nil {
		t.Error("expected error when adding duplicate player")
	}
}

func TestSetState(t *testing.T) {
	draft := NewDraft("tournament-1", "table-1", []string{"Alice", "Bob"}, 3, 15)

	draft.SetState(DraftStatePicking)
	if draft.GetState() != DraftStatePicking {
		t.Errorf("expected state PICKING, got %s", draft.GetState().String())
	}

	// StartTime should be set when entering PICKING
	if draft.StartTime == nil {
		t.Error("expected start time to be set")
	}

	draft.SetState(DraftStateFinished)
	if draft.EndTime == nil {
		t.Error("expected end time to be set")
	}
}

func TestPickCard(t *testing.T) {
	draft := NewDraft("tournament-1", "table-1", []string{"Alice"}, 3, 15)

	// Set up a booster for Alice
	cards := []*Card{
		{ID: "card-1", Name: "Card 1"},
		{ID: "card-2", Name: "Card 2"},
		{ID: "card-3", Name: "Card 3"},
	}
	draft.SetCurrentBooster("Alice", cards)

	// Pick a card
	err := draft.PickCard("Alice", "card-2")
	if err != nil {
		t.Fatalf("failed to pick card: %v", err)
	}

	// Check that card was added to picks
	player := draft.Players["Alice"]
	if len(player.Picks) != 1 {
		t.Errorf("expected 1 pick, got %d", len(player.Picks))
	}

	if player.Picks[0].ID != "card-2" {
		t.Errorf("expected picked card 'card-2', got '%s'", player.Picks[0].ID)
	}

	// Check that card was removed from booster
	if len(player.CurrentBooster.Cards) != 2 {
		t.Errorf("expected 2 cards left in booster, got %d", len(player.CurrentBooster.Cards))
	}
}

func TestPassBoosters(t *testing.T) {
	players := []string{"Alice", "Bob", "Charlie"}
	draft := NewDraft("tournament-1", "table-1", players, 3, 15)

	// Set up boosters for each player
	draft.SetCurrentBooster("Alice", []*Card{{ID: "a1"}, {ID: "a2"}})
	draft.SetCurrentBooster("Bob", []*Card{{ID: "b1"}, {ID: "b2"}})
	draft.SetCurrentBooster("Charlie", []*Card{{ID: "c1"}, {ID: "c2"}})

	// Pass boosters (direction = 1, so left)
	err := draft.PassBoosters()
	if err != nil {
		t.Fatalf("failed to pass boosters: %v", err)
	}

	// Alice's booster should now be Charlie's original booster
	aliceBooster := draft.Players["Alice"].CurrentBooster
	if aliceBooster.Cards[0].ID != "c1" {
		t.Errorf("expected Alice to receive Charlie's booster")
	}

	// Bob should receive Alice's original booster
	bobBooster := draft.Players["Bob"].CurrentBooster
	if bobBooster.Cards[0].ID != "a1" {
		t.Errorf("expected Bob to receive Alice's booster")
	}

	// Charlie should receive Bob's original booster
	charlieBooster := draft.Players["Charlie"].CurrentBooster
	if charlieBooster.Cards[0].ID != "b1" {
		t.Errorf("expected Charlie to receive Bob's booster")
	}

	// Pick counter should increment
	if draft.CurrentPick != 2 {
		t.Errorf("expected CurrentPick to be 2, got %d", draft.CurrentPick)
	}
}

func TestDraftCompletion(t *testing.T) {
	draft := NewDraft("tournament-1", "table-1", []string{"Alice", "Bob"}, 2, 3)
	draft.CurrentPick = 3
	draft.CurrentPack = 1

	// Pass boosters at end of pack
	draft.PassBoosters()

	// Should move to next pack
	if draft.CurrentPack != 2 {
		t.Errorf("expected CurrentPack to be 2, got %d", draft.CurrentPack)
	}

	if draft.CurrentPick != 1 {
		t.Errorf("expected CurrentPick to reset to 1, got %d", draft.CurrentPick)
	}

	// Direction should reverse
	if draft.PassDirection != -1 {
		t.Errorf("expected PassDirection to be -1, got %d", draft.PassDirection)
	}

	// Complete second pack
	draft.CurrentPick = 3
	draft.PassBoosters()

	// Draft should be finished
	if draft.State != DraftStateFinished {
		t.Errorf("expected draft to be finished, got %s", draft.State.String())
	}
}

func TestGetDraft(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(logger)

	draft := mgr.CreateDraft("tournament-1", "table-1", []string{"Alice"}, 3, 15)
	draftID := draft.ID

	// Retrieve draft
	retrieved, ok := mgr.GetDraft(draftID)
	if !ok {
		t.Fatal("failed to retrieve draft")
	}

	if retrieved.ID != draftID {
		t.Errorf("expected draft ID '%s', got '%s'", draftID, retrieved.ID)
	}

	// Non-existent draft
	_, ok = mgr.GetDraft("non-existent")
	if ok {
		t.Error("expected non-existent draft to not be found")
	}
}

func TestRemoveDraft(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := NewManager(logger)

	draft := mgr.CreateDraft("tournament-1", "table-1", []string{"Alice"}, 3, 15)
	draftID := draft.ID

	// Remove draft
	mgr.RemoveDraft(draftID)

	// Should no longer exist
	_, ok := mgr.GetDraft(draftID)
	if ok {
		t.Error("expected draft to be removed")
	}
}
