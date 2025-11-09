package integration

import (
	"math"
	"testing"

	"github.com/magefree/mage-server-go/internal/rating"
)

// TestRatingSystemIntegration tests the rating system with realistic scenarios
func TestRatingSystemIntegration(t *testing.T) {
	calc := rating.NewCalculator()

	t.Run("NewPlayerProgression", func(t *testing.T) {
		// New player starts at 1500 rating, 350 RD
		player := rating.NewPlayer()

		if player.Rating != 1500.0 {
			t.Errorf("expected rating 1500, got %f", player.Rating)
		}

		// Player wins against equal opponent
		results := []rating.MatchResult{
			{OpponentRating: 1500.0, OpponentDeviation: 350.0, Score: 1.0},
		}

		player = calc.UpdateRating(player, results)

		// Rating should increase after win
		if player.Rating <= 1500.0 {
			t.Errorf("expected rating increase, got %f", player.Rating)
		}

		// Deviation should decrease (more certain)
		if player.Deviation >= 350.0 {
			t.Errorf("expected deviation decrease, got %f", player.Deviation)
		}

		t.Logf("After 1 win: Rating=%.2f, RD=%.2f", player.Rating, player.Deviation)
	})

	t.Run("WinStreak", func(t *testing.T) {
		player := rating.NewPlayer()
		startRating := player.Rating

		// Win 5 games against equal opponents
		for i := 0; i < 5; i++ {
			results := []rating.MatchResult{
				{OpponentRating: 1500.0, OpponentDeviation: 200.0, Score: 1.0},
			}
			player = calc.UpdateRating(player, results)
		}

		// Rating should be significantly higher
		if player.Rating <= startRating+50 {
			t.Errorf("expected significant rating increase after 5 wins, got %f", player.Rating)
		}

		// Deviation should be much lower
		if player.Deviation >= 250.0 {
			t.Errorf("expected low deviation after 5 games, got %f", player.Deviation)
		}

		t.Logf("After 5 wins: Rating=%.2f, RD=%.2f", player.Rating, player.Deviation)
	})

	t.Run("LossStreak", func(t *testing.T) {
		player := rating.NewPlayer()
		startRating := player.Rating

		// Lose 5 games against equal opponents
		for i := 0; i < 5; i++ {
			results := []rating.MatchResult{
				{OpponentRating: 1500.0, OpponentDeviation: 200.0, Score: 0.0},
			}
			player = calc.UpdateRating(player, results)
		}

		// Rating should be significantly lower
		if player.Rating >= startRating-50 {
			t.Errorf("expected significant rating decrease after 5 losses, got %f", player.Rating)
		}

		t.Logf("After 5 losses: Rating=%.2f, RD=%.2f", player.Rating, player.Deviation)
	})

	t.Run("MixedResults", func(t *testing.T) {
		player := rating.NewPlayer()

		// Win, Loss, Win, Loss, Draw pattern
		patterns := []float64{1.0, 0.0, 1.0, 0.0, 0.5}
		for _, score := range patterns {
			results := []rating.MatchResult{
				{OpponentRating: 1500.0, OpponentDeviation: 200.0, Score: score},
			}
			player = calc.UpdateRating(player, results)
		}

		// Rating should be close to starting (2.5 / 5 = 50% win rate)
		ratingDiff := math.Abs(player.Rating - 1500.0)
		if ratingDiff > 100.0 {
			t.Errorf("expected rating near 1500 after even results, got %f", player.Rating)
		}

		t.Logf("After mixed results: Rating=%.2f, RD=%.2f", player.Rating, player.Deviation)
	})

	t.Run("StrongerOpponents", func(t *testing.T) {
		player := &rating.Player{
			Rating:     1400.0,
			Deviation:  200.0,
			Volatility: 0.06,
		}

		// Win against much stronger opponent (1700 rating)
		results := []rating.MatchResult{
			{OpponentRating: 1700.0, OpponentDeviation: 150.0, Score: 1.0},
		}

		newPlayer := calc.UpdateRating(player, results)

		// Should gain more rating than beating equal opponent
		ratingGain := newPlayer.Rating - player.Rating

		// Compare to beating equal opponent
		equalPlayer := &rating.Player{
			Rating:     1400.0,
			Deviation:  200.0,
			Volatility: 0.06,
		}
		equalResults := []rating.MatchResult{
			{OpponentRating: 1400.0, OpponentDeviation: 150.0, Score: 1.0},
		}
		equalNew := calc.UpdateRating(equalPlayer, equalResults)
		equalGain := equalNew.Rating - equalPlayer.Rating

		if ratingGain <= equalGain {
			t.Errorf("beating stronger opponent should give more rating: got %.2f vs %.2f",
				ratingGain, equalGain)
		}

		t.Logf("Beat stronger opponent: +%.2f rating", ratingGain)
		t.Logf("Beat equal opponent: +%.2f rating", equalGain)
	})

	t.Run("Inactivity", func(t *testing.T) {
		player := &rating.Player{
			Rating:     1600.0,
			Deviation:  100.0, // Low deviation (many games played)
			Volatility: 0.06,
		}

		// No games played (empty results)
		newPlayer := calc.UpdateRating(player, []rating.MatchResult{})

		// Rating should stay same
		if newPlayer.Rating != player.Rating {
			t.Errorf("rating should not change during inactivity, got %f -> %f",
				player.Rating, newPlayer.Rating)
		}

		// Deviation should increase (uncertainty grows)
		if newPlayer.Deviation <= player.Deviation {
			t.Errorf("deviation should increase during inactivity, got %f -> %f",
				player.Deviation, newPlayer.Deviation)
		}

		t.Logf("After inactivity: Rating=%.2f (unchanged), RD=%.2f -> %.2f",
			newPlayer.Rating, player.Deviation, newPlayer.Deviation)
	})

	t.Run("MultipleMatchesInPeriod", func(t *testing.T) {
		player := rating.NewPlayer()

		// Play 3 matches in one rating period (common in tournaments)
		results := []rating.MatchResult{
			{OpponentRating: 1450.0, OpponentDeviation: 200.0, Score: 1.0}, // Win
			{OpponentRating: 1550.0, OpponentDeviation: 180.0, Score: 0.5}, // Draw
			{OpponentRating: 1600.0, OpponentDeviation: 150.0, Score: 0.0}, // Loss
		}

		newPlayer := calc.UpdateRating(player, results)

		// Should have valid rating and deviation
		if newPlayer.Rating < 1000 || newPlayer.Rating > 2000 {
			t.Errorf("unexpected rating after multiple matches: %f", newPlayer.Rating)
		}

		if newPlayer.Deviation < 50 || newPlayer.Deviation > 350 {
			t.Errorf("unexpected deviation after multiple matches: %f", newPlayer.Deviation)
		}

		t.Logf("After 3 matches (1W 1D 1L): Rating=%.2f, RD=%.2f",
			newPlayer.Rating, newPlayer.Deviation)
	})
}

// TestRatingCalculatorMath tests the mathematical functions
func TestRatingCalculatorMath(t *testing.T) {
	calc := rating.NewCalculator()

	t.Run("MathFunctions", func(t *testing.T) {
		// Note: g() is private, tested indirectly through E()
		// E function uses g internally, so we test E behavior

		// Test that E works correctly - equal players should have 0.5 expected score
		e := calc.E(0, 0, 1.0)
		if math.Abs(e-0.5) > 0.01 {
			t.Errorf("E(0,0,1.0) should be ≈0.5, got %f", e)
		}
	})

	t.Run("EFunction", func(t *testing.T) {
		// E(0,0) should be 0.5 (equal players)
		e := calc.E(0, 0, 1.0)
		if math.Abs(e-0.5) > 0.01 {
			t.Errorf("E(0,0) should be ≈0.5, got %f", e)
		}

		// E for stronger player should be > 0.5
		eStrong := calc.E(1.0, 0, 1.0)
		if eStrong <= 0.5 {
			t.Errorf("E(1,0) should be >0.5, got %f", eStrong)
		}

		// E for weaker player should be < 0.5
		eWeak := calc.E(-1.0, 0, 1.0)
		if eWeak >= 0.5 {
			t.Errorf("E(-1,0) should be <0.5, got %f", eWeak)
		}

		// E should always be in (0, 1)
		if eStrong <= 0 || eStrong >= 1 {
			t.Errorf("E should be in (0,1), got %f", eStrong)
		}
	})
}
