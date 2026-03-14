package mage.cards.a;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssertPerfection extends CardImpl {

    public AssertPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control gets +1/+0 until end of turn. It deals damage equal to its power to up to one target creature an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("it"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 1));
    }

    private AssertPerfection(final AssertPerfection card) {
        super(card);
    }

    @Override
    public AssertPerfection copy() {
        return new AssertPerfection(this);
    }
}
