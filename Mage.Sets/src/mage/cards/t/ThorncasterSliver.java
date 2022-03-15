
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThorncasterSliver extends CardImpl {

    public ThorncasterSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "Whenever this creature attacks, it deals 1 damage to any target."
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(ability,
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS)
                        .setText("Sliver creatures you control have \"Whenever this creature attacks, it deals 1 damage to any target.\"")));
    }

    private ThorncasterSliver(final ThorncasterSliver card) {
        super(card);
    }

    @Override
    public ThorncasterSliver copy() {
        return new ThorncasterSliver(this);
    }
}
