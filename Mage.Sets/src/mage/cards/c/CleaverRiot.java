
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class CleaverRiot extends CardImpl {

    public CleaverRiot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Creatures you control gain double strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false));
    }

    private CleaverRiot(final CleaverRiot card) {
        super(card);
    }

    @Override
    public CleaverRiot copy() {
        return new CleaverRiot(this);
    }
}
