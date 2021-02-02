
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class HeadlongRush extends CardImpl {

    public HeadlongRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Attacking creatures gain first strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, new FilterAttackingCreature()));
    }

    private HeadlongRush(final HeadlongRush card) {
        super(card);
    }

    @Override
    public HeadlongRush copy() {
        return new HeadlongRush(this);
    }
}
