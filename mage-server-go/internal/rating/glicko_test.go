package rating

import (
	"math"
	"testing"
)

func TestNewPlayer(t *testing.T) {
	player := NewPlayer()

	if player.Rating != DefaultRating {
		t.Errorf("expected rating %f, got %f", DefaultRating, player.Rating)
	}

	if player.Deviation != DefaultDeviation {
		t.Errorf("expected deviation %f, got %f", DefaultDeviation, player.Deviation)
	}

	if player.Volatility != DefaultVolatility {
		t.Errorf("expected volatility %f, got %f", DefaultVolatility, player.Volatility)
	}
}

func TestUpdateRatingAfterWin(t *testing.T) {
	calc := NewCalculator()
	player := NewPlayer()

	// Player wins against equal opponent
	results := []MatchResult{
		{
			OpponentRating:    1500.0,
			OpponentDeviation: 350.0,
			Score:             1.0, // Win
		},
	}

	updated := calc.UpdateRating(player, results)

	// Rating should increase after win
	if updated.Rating <= player.Rating {
		t.Errorf("expected rating to increase after win, got %f -> %f",
			player.Rating, updated.Rating)
	}

	// Deviation should decrease after playing
	if updated.Deviation >= player.Deviation {
		t.Errorf("expected deviation to decrease after playing, got %f -> %f",
			player.Deviation, updated.Deviation)
	}
}

func TestUpdateRatingAfterLoss(t *testing.T) {
	calc := NewCalculator()
	player := NewPlayer()

	// Player loses against equal opponent
	results := []MatchResult{
		{
			OpponentRating:    1500.0,
			OpponentDeviation: 350.0,
			Score:             0.0, // Loss
		},
	}

	updated := calc.UpdateRating(player, results)

	// Rating should decrease after loss
	if updated.Rating >= player.Rating {
		t.Errorf("expected rating to decrease after loss, got %f -> %f",
			player.Rating, updated.Rating)
	}

	// Deviation should still decrease after playing
	if updated.Deviation >= player.Deviation {
		t.Errorf("expected deviation to decrease after playing, got %f -> %f",
			player.Deviation, updated.Deviation)
	}
}

func TestUpdateRatingAfterDraw(t *testing.T) {
	calc := NewCalculator()
	player := NewPlayer()

	// Player draws against equal opponent
	results := []MatchResult{
		{
			OpponentRating:    1500.0,
			OpponentDeviation: 350.0,
			Score:             0.5, // Draw
		},
	}

	updated := calc.UpdateRating(player, results)

	// Rating should stay approximately the same (within 1 point)
	if math.Abs(updated.Rating-player.Rating) > 1.0 {
		t.Errorf("expected rating to stay similar after draw, got %f -> %f",
			player.Rating, updated.Rating)
	}

	// Deviation should decrease after playing
	if updated.Deviation >= player.Deviation {
		t.Errorf("expected deviation to decrease after playing, got %f -> %f",
			player.Deviation, updated.Deviation)
	}
}

func TestUpdateRatingMultipleMatches(t *testing.T) {
	calc := NewCalculator()
	player := NewPlayer()

	// Player plays multiple matches
	results := []MatchResult{
		{OpponentRating: 1400.0, OpponentDeviation: 30.0, Score: 1.0}, // Win
		{OpponentRating: 1550.0, OpponentDeviation: 100.0, Score: 0.0}, // Loss
		{OpponentRating: 1700.0, OpponentDeviation: 300.0, Score: 0.0}, // Loss
	}

	updated := calc.UpdateRating(player, results)

	// Should have valid values
	if updated.Rating < 1000 || updated.Rating > 2000 {
		t.Errorf("unexpected rating after multiple matches: %f", updated.Rating)
	}

	// Deviation should decrease significantly after 3 matches
	if updated.Deviation >= player.Deviation*0.9 {
		t.Errorf("expected significant deviation decrease, got %f -> %f",
			player.Deviation, updated.Deviation)
	}
}

func TestUnplayedPeriod(t *testing.T) {
	calc := NewCalculator()
	player := &Player{
		Rating:     1500.0,
		Deviation:  200.0,
		Volatility: 0.06,
	}

	// No matches played
	updated := calc.UpdateRating(player, []MatchResult{})

	// Rating should stay the same
	if updated.Rating != player.Rating {
		t.Errorf("expected rating to stay same with no matches, got %f -> %f",
			player.Rating, updated.Rating)
	}

	// Deviation should increase (uncertainty grows)
	if updated.Deviation <= player.Deviation {
		t.Errorf("expected deviation to increase with no matches, got %f -> %f",
			player.Deviation, updated.Deviation)
	}

	// Volatility should stay the same
	if updated.Volatility != player.Volatility {
		t.Errorf("expected volatility to stay same with no matches, got %f -> %f",
			player.Volatility, updated.Volatility)
	}
}

func TestCalculateMatchRating(t *testing.T) {
	calc := NewCalculator()

	// Simple win against equal opponent
	newRating, newDeviation, newVolatility := calc.CalculateMatchRating(
		1500.0, 350.0, 0.06, // Player
		1500.0, 350.0,       // Opponent
		1.0,                 // Win
	)

	// Rating should increase
	if newRating <= 1500.0 {
		t.Errorf("expected rating to increase after win, got %f", newRating)
	}

	// Deviation should decrease
	if newDeviation >= 350.0 {
		t.Errorf("expected deviation to decrease, got %f", newDeviation)
	}

	// Volatility should be valid
	if newVolatility <= 0 || newVolatility > 0.5 {
		t.Errorf("unexpected volatility: %f", newVolatility)
	}
}

func TestGFunction(t *testing.T) {
	calc := NewCalculator()

	// g(0) should be close to 1
	g0 := calc.g(0)
	if math.Abs(g0-1.0) > 0.01 {
		t.Errorf("expected g(0) ≈ 1, got %f", g0)
	}

	// g(large) should be smaller
	gLarge := calc.g(2.0)
	if gLarge >= 1.0 {
		t.Errorf("expected g(large) < 1, got %f", gLarge)
	}
}

func TestEFunction(t *testing.T) {
	calc := NewCalculator()

	// E for equal players should be 0.5
	e := calc.E(0, 0, 1.0)
	if math.Abs(e-0.5) > 0.01 {
		t.Errorf("expected E(0,0) ≈ 0.5, got %f", e)
	}

	// E for stronger player should be > 0.5
	eStrong := calc.E(1.0, 0, 1.0)
	if eStrong <= 0.5 {
		t.Errorf("expected E(1,0) > 0.5, got %f", eStrong)
	}

	// E for weaker player should be < 0.5
	eWeak := calc.E(-1.0, 0, 1.0)
	if eWeak >= 0.5 {
		t.Errorf("expected E(-1,0) < 0.5, got %f", eWeak)
	}
}
