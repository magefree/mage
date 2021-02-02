
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class RainOfEmbers extends CardImpl {

    public RainOfEmbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Rain of Embers deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(1));
    }

    private RainOfEmbers(final RainOfEmbers card) {
        super(card);
    }

    @Override
    public RainOfEmbers copy() {
        return new RainOfEmbers(this);
    }
}
