
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.BeastToken3;

/**
 *
 * @author fireshoes
 */
public final class TrialOfStrength extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cartouche");

    static {
        filter.add(SubType.CARTOUCHE.getPredicate());
    }

    public TrialOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When Trial of Strength enters the battlefield, create a 4/2 green Beast creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BeastToken3())));

        // When a Cartouche enters the battlefield under your control, return Trial of Strength to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter
        ));
    }

    private TrialOfStrength(final TrialOfStrength card) {
        super(card);
    }

    @Override
    public TrialOfStrength copy() {
        return new TrialOfStrength(this);
    }
}
