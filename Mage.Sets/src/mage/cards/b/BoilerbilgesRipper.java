package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoilerbilgesRipper extends CardImpl {

    public BoilerbilgesRipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Boilerbilges Ripper enters, you may sacrifice another creature or enchantment. When you do, Boilerbilges Ripper deals 2 damage to any target.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(2), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ANOTHER_CREATURE_OR_ENCHANTMENT),
                "Sacrifice another creature or enchantment?"
        )));
    }

    private BoilerbilgesRipper(final BoilerbilgesRipper card) {
        super(card);
    }

    @Override
    public BoilerbilgesRipper copy() {
        return new BoilerbilgesRipper(this);
    }
}
