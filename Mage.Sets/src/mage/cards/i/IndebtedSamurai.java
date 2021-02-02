
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IndebtedSamurai extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Samurai you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.SAMURAI.getPredicate());
    }

    public IndebtedSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));

        // Whenever a Samurai you control dies, you may put a +1/+1 counter on Indebted Samurai.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true, filter));
    }

    private IndebtedSamurai(final IndebtedSamurai card) {
        super(card);
    }

    @Override
    public IndebtedSamurai copy() {
        return new IndebtedSamurai(this);
    }
}