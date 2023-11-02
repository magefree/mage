package mage.cards.s;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SongOfTotentanz extends CardImpl {

    public SongOfTotentanz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Create X 1/1 black Rat creature tokens with “This creature can’t block.” Creatures you control gain haste until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RatCantBlockToken(), ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private SongOfTotentanz(final SongOfTotentanz card) {
        super(card);
    }

    @Override
    public SongOfTotentanz copy() {
        return new SongOfTotentanz(this);
    }
}
