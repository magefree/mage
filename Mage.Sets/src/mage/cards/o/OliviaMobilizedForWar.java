
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author fireshoes
 */
public final class OliviaMobilizedForWar extends CardImpl {

    public OliviaMobilizedForWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature enters the battlefield under your control, you may discard a card. If you do, put a +1/+1 counter on that creature,
        // it gains haste until end of turn, and it becomes a Vampire in addition to its other types.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("put a +1/+1 counter on that creature");
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(effect, new DiscardCardCost());
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", it gains haste until end of turn,");
        doIfCostPaid.addEffect(effect);
        effect = new BecomesCreatureTypeTargetEffect(Duration.WhileOnBattlefield, SubType.VAMPIRE, false);
        effect.setText("and it becomes a Vampire in addition to its other types");
        doIfCostPaid.addEffect(effect);
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, doIfCostPaid,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT, null));
    }

    private OliviaMobilizedForWar(final OliviaMobilizedForWar card) {
        super(card);
    }

    @Override
    public OliviaMobilizedForWar copy() {
        return new OliviaMobilizedForWar(this);
    }
}
