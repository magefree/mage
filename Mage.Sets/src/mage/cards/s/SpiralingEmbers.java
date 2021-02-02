
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class SpiralingEmbers extends CardImpl {

    public SpiralingEmbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");
        this.subtype.add(SubType.ARCANE);


        // Spiraling Embers deals damage to any target equal to the number of cards in your hand.
        Effect effect = new DamageTargetEffect(CardsInControllerHandCount.instance);
        effect.setText("{this} deals damage to any target equal to the number of cards in your hand.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SpiralingEmbers(final SpiralingEmbers card) {
        super(card);
    }

    @Override
    public SpiralingEmbers copy() {
        return new SpiralingEmbers(this);
    }
}
