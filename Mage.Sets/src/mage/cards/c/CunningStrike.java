
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author fireshoes
 */
public final class CunningStrike extends CardImpl {

    public CunningStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Cunning Strike deals 2 damage to target creature and 2 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(StaticValue.get(2), true, "", true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new DamageTargetEffect(StaticValue.get(2), true, "", true);
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("and 2 damage to target player");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CunningStrike(final CunningStrike card) {
        super(card);
    }

    @Override
    public CunningStrike copy() {
        return new CunningStrike(this);
    }
}
