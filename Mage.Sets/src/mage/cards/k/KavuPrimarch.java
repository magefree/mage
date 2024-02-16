
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class KavuPrimarch extends CardImpl {

    public KavuPrimarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {4} (You may pay an additional {4} as you cast this spell.)
        this.addAbility(new KickerAbility("{4}"));

        // Convoke (Each creature you tap while casting this spell reduces its cost by {1} or by one mana of that creature's color.)
        this.addAbility(new ConvokeAbility());


        // If Kavu Primarch was kicked, it enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),KickedCondition.ONCE,
                "If Kavu Primarch was kicked, it enters the battlefield with four +1/+1 counters on it.", ""));
    }

    private KavuPrimarch(final KavuPrimarch card) {
        super(card);
    }

    @Override
    public KavuPrimarch copy() {
        return new KavuPrimarch(this);
    }
}
