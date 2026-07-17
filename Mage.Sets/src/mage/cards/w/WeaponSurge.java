
package mage.cards.w;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */

public final class WeaponSurge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All creatures you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public WeaponSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target creature you control gets +1/+0 and gains first strike until end of turn.
        // Overload {1}{R}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{1}{R}"), new TargetControlledCreaturePermanent(),
                new BoostTargetEffect(1,0, Duration.EndOfTurn).setText("target creature you control gets +1/+0"),
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and gains first strike until end of turn"));
    }

    private WeaponSurge(final WeaponSurge card) {
        super(card);
    }

    @Override
    public WeaponSurge copy() {
        return new WeaponSurge(this);
    }

}
