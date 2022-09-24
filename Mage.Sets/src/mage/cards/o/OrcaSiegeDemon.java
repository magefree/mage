package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OrcaSiegeDemon extends CardImpl {

    public OrcaSiegeDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another creature dies, put a +1/+1 counter on Orca, Siege Demon.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));

        // When Orca dies, it deals damage equal to its power divided as you choose among any number of targets.
        Ability ability = new DiesSourceTriggeredAbility(new DamageMultiEffect(new SourcePermanentPowerCount())
                .setText("it deals damage equal to its power divided as you choose among any number of targets."));
        ability.addTarget(new TargetAnyTargetAmount(new SourcePermanentPowerCount()));
        this.addAbility(ability);
    }

    private OrcaSiegeDemon(final OrcaSiegeDemon card) {
        super(card);
    }

    @Override
    public OrcaSiegeDemon copy() {
        return new OrcaSiegeDemon(this);
    }
}
