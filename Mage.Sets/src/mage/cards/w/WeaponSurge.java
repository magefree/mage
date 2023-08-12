
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
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
        this.getSpellAbility().addEffect(new BoostTargetEffect(1,0, Duration.EndOfTurn).setText("target creature you control gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {1}{R}
        Ability ability = new OverloadAbility(this, new BoostAllEffect(1,0, Duration.EndOfTurn, filter, false), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter, false));
        this.addAbility(ability);
    }

    private WeaponSurge(final WeaponSurge card) {
        super(card);
    }

    @Override
    public WeaponSurge copy() {
        return new WeaponSurge(this);
    }

}
