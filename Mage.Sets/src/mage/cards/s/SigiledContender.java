package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigiledContender extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public SigiledContender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sigiled Contender has lifelink as long as it has a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        LifelinkAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "{this} has lifelink as long as it has a +1/+1 counter on it"
        )));
    }

    private SigiledContender(final SigiledContender card) {
        super(card);
    }

    @Override
    public SigiledContender copy() {
        return new SigiledContender(this);
    }
}
