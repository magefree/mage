package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SuperCombo extends CardImpl {

    public SuperCombo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Replicate {2}
        this.addAbility(new ReplicateAbility("{2}"));

        // Target creature you control deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private SuperCombo(final SuperCombo card) {
        super(card);
    }

    @Override
    public SuperCombo copy() {
        return new SuperCombo(this);
    }
}
