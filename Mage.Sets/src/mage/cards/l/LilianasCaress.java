
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LilianasCaress extends CardImpl {

    public LilianasCaress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever an opponent discards a card, that player loses 2 life.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(new LoseLifeTargetEffect(2), false, SetTargetPointer.PLAYER));
    }

    private LilianasCaress(final LilianasCaress card) {
        super(card);
    }

    @Override
    public LilianasCaress copy() {
        return new LilianasCaress(this);
    }

}
