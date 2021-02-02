
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward
 */
public final class ParallelLives extends CardImpl {

    public ParallelLives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CreateTwiceThatManyTokensEffect()));
    }

    private ParallelLives(final ParallelLives card) {
        super(card);
    }

    @Override
    public ParallelLives copy() {
        return new ParallelLives(this);
    }
}
