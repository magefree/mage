
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class Thunderblust extends CardImpl {

    private static final String rule = "{this} has trample as long as it has a -1/-1 counter on it";

    public Thunderblust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Thunderblust has trample as long as it has a -1/-1 counter on it.
        Effect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), new SourceHasCounterCondition(CounterType.M1M1), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Persist
        this.addAbility(new PersistAbility());
    }

    private Thunderblust(final Thunderblust card) {
        super(card);
    }

    @Override
    public Thunderblust copy() {
        return new Thunderblust(this);
    }
}
