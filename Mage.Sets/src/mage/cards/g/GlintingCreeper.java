package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlintingCreeper extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ColorsOfManaSpentToCastCount.getInstance(), 2);

    public GlintingCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Converge — Glinting Creeper enters the battlefield with two +1/+1 counters on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), xValue, true
        ), null, "<i>Converge</i> &mdash; {this} enters the battlefield " +
                "with two +1/+1 counters on it for each color of mana spent to cast it.", null));

        // Glinting Creeper can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private GlintingCreeper(final GlintingCreeper card) {
        super(card);
    }

    @Override
    public GlintingCreeper copy() {
        return new GlintingCreeper(this);
    }
}
