
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class FatefulShowdown extends CardImpl {

    public FatefulShowdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // Fateful Showdown deals damage to any target equal to the number of cards in your hand. Discard all the cards in your hand, then draw that many cards.
        Effect effect = new DamageTargetEffect(CardsInControllerHandCount.instance);
        effect.setText("{this} deals damage to any target equal to the number of cards in your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DiscardHandDrawSameNumberSourceEffect());

    }

    private FatefulShowdown(final FatefulShowdown card) {
        super(card);
    }

    @Override
    public FatefulShowdown copy() {
        return new FatefulShowdown(this);
    }
}
