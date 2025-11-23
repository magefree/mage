package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackAloneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PipsqueakRebelStrongarm extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, ComparisonType.EQUAL_TO, 0);

    public PipsqueakRebelStrongarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Pipsqueak can't attack alone unless he has a +1/+1 counter on him.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackAloneSourceEffect(), condition,
                "{this} can't attack alone unless he has a +1/+1 counter on him"
        )));
    }

    private PipsqueakRebelStrongarm(final PipsqueakRebelStrongarm card) {
        super(card);
    }

    @Override
    public PipsqueakRebelStrongarm copy() {
        return new PipsqueakRebelStrongarm(this);
    }
}
