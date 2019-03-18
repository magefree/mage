
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WingsOfVelisVel extends CardImpl {

    public WingsOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());

        // Target creature becomes 4/4, gains all creature types, and gains flying until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new SetPowerToughnessTargetEffect(4, 4, Duration.EndOfTurn);
        effect.setText("Target creature becomes 4/4");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(ChangelingAbility.getInstance(), Duration.EndOfTurn, null, false, Layer.TypeChangingEffects_4, SubLayer.NA);
        effect.setText(", gains all creature types");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", and gains flying until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    public WingsOfVelisVel(final WingsOfVelisVel card) {
        super(card);
    }

    @Override
    public WingsOfVelisVel copy() {
        return new WingsOfVelisVel(this);
    }
}
