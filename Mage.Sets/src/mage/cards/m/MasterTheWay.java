
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class MasterTheWay extends CardImpl {

    public MasterTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{R}");


        // Draw a card. Master the Way deals damage to any target equal to the number of cards in your hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        Effect effect = new DamageTargetEffect(CardsInControllerHandCount.instance);
        effect.setText("{this} deals damage to any target equal to the number of cards in your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private MasterTheWay(final MasterTheWay card) {
        super(card);
    }

    @Override
    public MasterTheWay copy() {
        return new MasterTheWay(this);
    }
}
