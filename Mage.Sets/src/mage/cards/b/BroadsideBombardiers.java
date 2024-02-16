package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostManaValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

/**
 * @author jam736
 */
public final class BroadsideBombardiers extends CardImpl {

    public BroadsideBombardiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Boast — Sacrifice another creature or artifact: Broadside Bombardiers deals damage equal to 2 plus the sacrificed permanent’s mana value to any target.
        Ability ability = new BoastAbility(new DamageTargetEffect(
                new IntPlusDynamicValue(2, SacrificeCostManaValue.PERMANENT))
                .setText("{this} deals damage equal to 2 plus the sacrificed permanent's mana value to any target."),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT_SHORT_TEXT)
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BroadsideBombardiers(final BroadsideBombardiers card) {
        super(card);
    }

    @Override
    public BroadsideBombardiers copy() {
        return new BroadsideBombardiers(this);
    }
}
