
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class TrialOfKnowledge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cartouche");

    static {
        filter.add(SubType.CARTOUCHE.getPredicate());
    }

    public TrialOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // When Trial of Knowledge enters the battlefield, draw three cards, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 1), false));

        // When a Cartouche enters the battlefield under your control, return Trial of Knowledge to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter,
                "When a Cartouche enters the battlefield under your control, return {this} to its owner's hand."));
    }

    private TrialOfKnowledge(final TrialOfKnowledge card) {
        super(card);
    }

    @Override
    public TrialOfKnowledge copy() {
        return new TrialOfKnowledge(this);
    }
}
