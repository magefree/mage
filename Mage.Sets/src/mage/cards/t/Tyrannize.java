
package mage.cards.t;

import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth using code from LevelX
 */
public final class Tyrannize extends CardImpl {
    
    private static final String rule = "Pay 7 life? (otherwise discard your hand)";

    public Tyrannize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B/R}{B/R}");

        // Target player discards their hand unless they pay 7 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                new DiscardHandTargetEffect(), new PayLifeCost(7)).withTheyText());
    }

    private Tyrannize(final Tyrannize card) {
        super(card);
    }

    @Override
    public Tyrannize copy() {
        return new Tyrannize(this);
    }
}
