
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class TrialOfZeal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cartouche");

    static {
        filter.add(SubType.CARTOUCHE.getPredicate());
    }

    public TrialOfZeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When Trial of Zeal enters the battlefield, it deals 3 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // When a Cartouche enters the battlefield under your control, return Trial of Zeal to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter
        ));
    }

    private TrialOfZeal(final TrialOfZeal card) {
        super(card);
    }

    @Override
    public TrialOfZeal copy() {
        return new TrialOfZeal(this);
    }
}
