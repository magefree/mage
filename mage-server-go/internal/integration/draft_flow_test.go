package integration

import (
	"testing"

	"github.com/magefree/mage-server-go/internal/draft"
	"go.uber.org/zap/zaptest"
)

// TestCompleteDraftFlow tests a complete draft from start to finish
func TestCompleteDraftFlow(t *testing.T) {
	logger := zaptest.NewLogger(t)
	mgr := draft.NewManager(logger)

	players := []string{"Alice", "Bob", "Charlie"}
	numPacks := 3
	cardsPerPack := 15

	// Create draft
	d := mgr.CreateDraft("tournament-1", "table-1", players, numPacks, cardsPerPack)
	if d == nil {
		t.Fatal("failed to create draft")
	}

	// Verify initial state
	if d.GetState() != draft.DraftStateWaiting {
		t.Errorf("expected WAITING state, got %s", d.GetState().String())
	}

	// Verify players
	if len(d.Players) != 3 {
		t.Errorf("expected 3 players, got %d", len(d.Players))
	}

	// Start draft
	d.SetState(draft.DraftStatePicking)
	if d.GetState() != draft.DraftStatePicking {
		t.Error("failed to set state to PICKING")
	}

	// Create mock boosters for each player
	for playerName := range d.Players {
		cards := make([]*draft.Card, cardsPerPack)
		for i := 0; i < cardsPerPack; i++ {
			cards[i] = &draft.Card{
				ID:   generateCardID(playerName, d.CurrentPack, i),
				Name: generateCardName(playerName, d.CurrentPack, i),
			}
		}
		d.SetCurrentBooster(playerName, cards)
	}

	// Simulate first pick for each player
	for playerName, player := range d.Players {
		if len(player.CurrentBooster.Cards) == 0 {
			t.Fatalf("player %s has no cards in booster", playerName)
		}

		// Pick first card
		cardID := player.CurrentBooster.Cards[0].ID
		err := d.PickCard(playerName, cardID)
		if err != nil {
			t.Fatalf("failed to pick card for %s: %v", playerName, err)
		}

		// Verify card was removed from booster
		if len(player.CurrentBooster.Cards) != cardsPerPack-1 {
			t.Errorf("expected %d cards in booster after pick, got %d",
				cardsPerPack-1, len(player.CurrentBooster.Cards))
		}

		// Verify card was added to picks
		if len(player.Picks) != 1 {
			t.Errorf("expected 1 picked card, got %d", len(player.Picks))
		}
	}

	// Pass boosters
	err := d.PassBoosters()
	if err != nil {
		t.Fatalf("failed to pass boosters: %v", err)
	}

	// Verify current pick incremented
	if d.CurrentPick != 2 {
		t.Errorf("expected current pick 2, got %d", d.CurrentPick)
	}

	// Verify boosters were passed (each player should have different booster)
	// In a 3-player draft passing left: Alice gets Charlie's, Bob gets Alice's, Charlie gets Bob's
}

// TestDraftBoosterPassing tests the booster passing mechanics
func TestDraftBoosterPassing(t *testing.T) {
	players := []string{"Alice", "Bob", "Charlie"}
	d := draft.NewDraft("tournament-1", "table-1", players, 3, 15)

	// Set up initial boosters with identifiable cards
	aliceCards := []*draft.Card{{ID: "a1"}, {ID: "a2"}}
	bobCards := []*draft.Card{{ID: "b1"}, {ID: "b2"}}
	charlieCards := []*draft.Card{{ID: "c1"}, {ID: "c2"}}

	d.SetCurrentBooster("Alice", aliceCards)
	d.SetCurrentBooster("Bob", bobCards)
	d.SetCurrentBooster("Charlie", charlieCards)

	// Record original boosters
	aliceOriginal := d.Players["Alice"].CurrentBooster.Cards[0].ID
	bobOriginal := d.Players["Bob"].CurrentBooster.Cards[0].ID
	charlieOriginal := d.Players["Charlie"].CurrentBooster.Cards[0].ID

	// Pass boosters (direction = 1, pass left)
	d.PassBoosters()

	// Verify boosters were passed correctly
	// Alice should receive Charlie's booster
	if d.Players["Alice"].CurrentBooster.Cards[0].ID == aliceOriginal {
		t.Error("Alice still has her original booster")
	}

	// Bob should receive Alice's booster
	if d.Players["Bob"].CurrentBooster.Cards[0].ID == bobOriginal {
		t.Error("Bob still has his original booster")
	}

	// Charlie should receive Bob's booster
	if d.Players["Charlie"].CurrentBooster.Cards[0].ID == charlieOriginal {
		t.Error("Charlie still has his original booster")
	}
}

// TestDraftCompletion tests draft completion conditions
func TestDraftCompletion(t *testing.T) {
	players := []string{"Alice", "Bob"}
	numPacks := 2
	cardsPerPack := 3

	d := draft.NewDraft("tournament-1", "table-1", players, numPacks, cardsPerPack)

	// Set state to picking
	d.SetState(draft.DraftStatePicking)

	// Set pack and pick to end of pack 1
	d.CurrentPack = 1
	d.CurrentPick = cardsPerPack

	// Pass boosters (should move to pack 2)
	d.PassBoosters()

	if d.CurrentPack != 2 {
		t.Errorf("expected pack 2, got %d", d.CurrentPack)
	}

	if d.CurrentPick != 1 {
		t.Errorf("expected pick 1 after moving to new pack, got %d", d.CurrentPick)
	}

	// Direction should reverse
	if d.PassDirection != -1 {
		t.Errorf("expected pass direction -1 (right), got %d", d.PassDirection)
	}

	// Complete pack 2
	d.CurrentPick = cardsPerPack
	d.PassBoosters()

	// Draft should be finished
	if d.State != draft.DraftStateFinished {
		t.Errorf("expected FINISHED state, got %s", d.State.String())
	}

	// End time should be set
	if d.EndTime == nil {
		t.Error("end time should be set when draft finishes")
	}
}

// Helper functions
func generateCardID(player string, pack, index int) string {
	return player + "-pack" + string(rune('0'+pack)) + "-card" + string(rune('0'+index))
}

func generateCardName(player string, pack, index int) string {
	return "Card from " + player + " Pack " + string(rune('0'+pack))
}
