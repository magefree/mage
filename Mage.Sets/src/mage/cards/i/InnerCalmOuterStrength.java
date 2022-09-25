package mage.cards.i;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class InnerCalmOuterStrength extends CardImpl {

    public InnerCalmOuterStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");
        this.subtype.add(SubType.ARCANE);

        // Target creature gets +X/+X until end of turn, where X is the number of cards in your hand.
        DynamicValue xValue= CardsInControllerHandCount.instance;
        Effect effect = new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn);
        effect.setText("Target creature gets +X/+X until end of turn, where X is the number of cards in your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InnerCalmOuterStrength(final InnerCalmOuterStrength card) {
        super(card);
    }

    @Override
    public InnerCalmOuterStrength copy() {
        return new InnerCalmOuterStrength(this);
    }
}
