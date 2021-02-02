
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author anonymous
 */
public final class WindsOfRebuke extends CardImpl {

    public WindsOfRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. Each player puts the top two cards of their library into their graveyard.
        getSpellAbility().addTarget(new TargetNonlandPermanent());
        getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getSpellAbility().addEffect(new MillCardsEachPlayerEffect(2, TargetController.ANY));
    }

    private WindsOfRebuke(final WindsOfRebuke card) {
        super(card);
    }

    @Override
    public WindsOfRebuke copy() {
        return new WindsOfRebuke(this);
    }
}
