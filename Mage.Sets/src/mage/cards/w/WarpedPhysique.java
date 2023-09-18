package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */

public final class WarpedPhysique extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(CardsInControllerHandCount.instance);

    public WarpedPhysique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}");

        // Target creature gets +X/-X until end of turn, where X is the number of cards in your hand.
        this.getSpellAbility().addEffect(new BoostTargetEffect(CardsInControllerHandCount.instance, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WarpedPhysique(final WarpedPhysique card) {
        super(card);
    }

    @Override
    public WarpedPhysique copy() {
        return new WarpedPhysique(this);
    }
}
