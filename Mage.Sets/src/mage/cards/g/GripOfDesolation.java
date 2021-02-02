
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class GripOfDesolation extends CardImpl {

    public GripOfDesolation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Exile target creature and target land.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new ExileTargetEffect();
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("and target land");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetLandPermanent());

    }

    private GripOfDesolation(final GripOfDesolation card) {
        super(card);
    }

    @Override
    public GripOfDesolation copy() {
        return new GripOfDesolation(this);
    }
}
