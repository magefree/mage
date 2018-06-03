
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AspectOfHydra extends CardImpl {

    public AspectOfHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Target creature gets +X/+X until end of turn, where X is your devotion to green.
        DynamicValue greenDevotion = new DevotionCount(ColoredManaSymbol.G);
        Effect effect = new BoostTargetEffect(greenDevotion, greenDevotion, Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/+X until end of turn, where X is your devotion to green");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public AspectOfHydra(final AspectOfHydra card) {
        super(card);
    }

    @Override
    public AspectOfHydra copy() {
        return new AspectOfHydra(this);
    }
}
