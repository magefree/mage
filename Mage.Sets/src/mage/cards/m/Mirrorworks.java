
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Mirrorworks extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("another nontoken artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public Mirrorworks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever another nontoken artifact enters the battlefield under your control, you may pay {2}.
        // If you do, create a token that's a copy of that artifact.
        Effect effect = new DoIfCostPaid(new CreateTokenCopyTargetEffect(true),
                new ManaCostsImpl<>("{2}"), "Create a token that's a copy of that artifact?");
        effect.setText("you may pay {2}. If you do, create a token that's a copy of that artifact");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filter, false, SetTargetPointer.PERMANENT, null));
    }

    private Mirrorworks(final Mirrorworks card) {
        super(card);
    }

    @Override
    public Mirrorworks copy() {
        return new Mirrorworks(this);
    }

}
