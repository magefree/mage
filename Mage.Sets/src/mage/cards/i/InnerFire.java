
package mage.cards.i;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class InnerFire extends CardImpl {

    public InnerFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Add {R} for each card in your hand.
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.RedMana(1), CardsInControllerHandCount.instance));
    }

    private InnerFire(final InnerFire card) {
        super(card);
    }

    @Override
    public InnerFire copy() {
        return new InnerFire(this);
    }
}
