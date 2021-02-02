
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AetherTradewinds extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public AetherTradewinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Return target permanent you control and target permanent you don't control to their owners' hands.
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        Effect effect = new ReturnToHandTargetEffect(true);
        effect.setText("Return target permanent you control and target permanent you don't control to their owners' hands");
        this.getSpellAbility().addEffect(effect);
    }

    private AetherTradewinds(final AetherTradewinds card) {
        super(card);
    }

    @Override
    public AetherTradewinds copy() {
        return new AetherTradewinds(this);
    }
}
