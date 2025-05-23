package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneDevourer extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(null);

    public BoneDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature enters with a number of +1/+1 counters on it equal to the number of creatures that died this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), CreaturesDiedThisTurnCount.instance, false
        ), "with a number of +1/+1 counters on it equal to the number of creatures that died this turn").addHint(CreaturesDiedThisTurnHint.instance));

        // When this creature dies, you draw X cards and you lose X life, where X is the number of +1/+1 counters on it.
        Ability ability = new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(xValue).setText("you draw X cards"));
        ability.addEffect(new LoseLifeSourceControllerEffect(xValue).setText("and you lose X life, where X is the number of +1/+1 counters on it"));
        this.addAbility(ability);
    }

    private BoneDevourer(final BoneDevourer card) {
        super(card);
    }

    @Override
    public BoneDevourer copy() {
        return new BoneDevourer(this);
    }
}
