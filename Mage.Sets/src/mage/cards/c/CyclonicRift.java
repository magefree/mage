
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class CyclonicRift extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public CyclonicRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent you don't control to its owner's hand.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filter));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        Effect effect = new ReturnToHandFromBattlefieldAllEffect(filter);
        effect.setText("Return each nonland permanent you don't control to its owner's hand");
        this.addAbility(new OverloadAbility(this, effect, new ManaCostsImpl<>("{6}{U}")));
    }

    private CyclonicRift(final CyclonicRift card) {
        super(card);
    }

    @Override
    public CyclonicRift copy() {
        return new CyclonicRift(this);
    }
}
