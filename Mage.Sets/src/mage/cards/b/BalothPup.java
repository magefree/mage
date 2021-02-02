
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class BalothPup extends CardImpl {

    private static final String rule = "{this} has trample as long as it has a +1/+1 counter on it";

    public BalothPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Baloth Pup has trample as long as it has a +1/+1 counter on it.
        Effect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), new SourceHasCounterCondition(CounterType.P1P1), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BalothPup(final BalothPup card) {
        super(card);
    }

    @Override
    public BalothPup copy() {
        return new BalothPup(this);
    }
}
