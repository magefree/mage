package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllPlayersEffect;
import mage.abilities.effects.common.continuous.CantLoseLifeAllEffect;
import mage.abilities.effects.common.continuous.CantLoseOrWinGameAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 *
 * @author padfoot
 */
public final class EverybodyLives extends CardImpl {

    public EverybodyLives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // All creatures gain hexproof and indestructible until end of turn. 
	this.getSpellAbility().addEffect(new GainAbilityAllEffect(
		HexproofAbility.getInstance(), 
		Duration.EndOfTurn, 
		StaticFilters.FILTER_PERMANENT_ALL_CREATURES
	).setText("all creatures gain hexproof"));
	this.getSpellAbility().addEffect(new GainAbilityAllEffect(
		IndestructibleAbility.getInstance(), 
		Duration.EndOfTurn, 
		StaticFilters.FILTER_PERMANENT_ALL_CREATURES
	).setText("and indestructible until end of turn"));
	// Players gain hexproof until end of turn.
	this.getSpellAbility().addEffect(new GainAbilityAllPlayersEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
	// Players can't lose life this turn and players can't lose the game or win the this turn.
        this.getSpellAbility().addEffect(new CantLoseLifeAllEffect(Duration.EndOfTurn, TargetController.ANY));
        this.getSpellAbility().addEffect(new CantLoseOrWinGameAllEffect(Duration.EndOfTurn).concatBy("and"));
    }

    private EverybodyLives(final EverybodyLives card) {
        super(card);
    }

    @Override
    public EverybodyLives copy() {
        return new EverybodyLives(this);
    }
}
