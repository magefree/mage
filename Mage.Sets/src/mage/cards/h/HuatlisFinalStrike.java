package mage.cards.h;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HuatlisFinalStrike extends CardImpl {

    public HuatlisFinalStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature you control gets +1/+0 until end of turn. It deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("it"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private HuatlisFinalStrike(final HuatlisFinalStrike card) {
        super(card);
    }

    @Override
    public HuatlisFinalStrike copy() {
        return new HuatlisFinalStrike(this);
    }
}
