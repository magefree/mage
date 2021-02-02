package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class RunawaySteamKin extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public RunawaySteamKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a red spell, if Runaway Steam-Kin has fewer than three +1/+1 counters on it, put a +1/+1 counter on Runaway Steam-Kin.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new AddCountersSourceEffect(
                                CounterType.P1P1.createInstance()
                        ), filter, false
                ), new SourceHasCounterCondition(CounterType.P1P1, 0, 2),
                "Whenever you cast a red spell, "
                + "if {this} has fewer than three +1/+1 counters on it, "
                + "put a +1/+1 counter on {this}."
        ));

        // Remove three +1/+1 counters from Runaway Steam-Kin: Add {R}{R}{R}.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.RedMana(3),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(3))
        ));
    }

    private RunawaySteamKin(final RunawaySteamKin card) {
        super(card);
    }

    @Override
    public RunawaySteamKin copy() {
        return new RunawaySteamKin(this);
    }
}
