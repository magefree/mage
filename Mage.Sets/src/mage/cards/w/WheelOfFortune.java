
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author mluds
 */
public final class WheelOfFortune extends CardImpl {

    public WheelOfFortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");


        // Each player discards their hand,
        this.getSpellAbility().addEffect(new DiscardHandAllEffect());
        // then draws seven cards.
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
    }

    private WheelOfFortune(final WheelOfFortune card) {
        super(card);
    }

    @Override
    public WheelOfFortune copy() {
        return new WheelOfFortune(this);
    }
}
