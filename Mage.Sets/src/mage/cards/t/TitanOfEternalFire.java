
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class TitanOfEternalFire extends CardImpl {

    public TitanOfEternalFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Each Human creature you control has "{R}, {T}: This creature deals 1 damage to any target."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        Effect effect = new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.HUMAN, "Each Human creature"));
        effect.setText("Each Human creature you control has \"{R}, {T}: This creature deals 1 damage to any target.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private TitanOfEternalFire(final TitanOfEternalFire card) {
        super(card);
    }

    @Override
    public TitanOfEternalFire copy() {
        return new TitanOfEternalFire(this);
    }
}
