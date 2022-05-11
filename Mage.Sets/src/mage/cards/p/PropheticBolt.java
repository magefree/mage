package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class PropheticBolt extends CardImpl {

    public PropheticBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{R}");


        // Prophetic Bolt deals 4 damage to any target. Look at the top four cards of your library. Put one of those cards into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private PropheticBolt(final PropheticBolt card) {
        super(card);
    }

    @Override
    public PropheticBolt copy() {
        return new PropheticBolt(this);
    }
}
