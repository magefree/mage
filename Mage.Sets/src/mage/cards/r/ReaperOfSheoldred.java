package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToThisTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ReaperOfSheoldred extends CardImpl {

    public ReaperOfSheoldred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Infect (This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(InfectAbility.getInstance());

        // Whenever a source deals damage to Reaper of Sheoldred, that source's controller gets a poison counter.
        this.addAbility(new SourceDealsDamageToThisTriggeredAbility(
                new AddCountersTargetEffect(CounterType.POISON.createInstance())
                        .setText("that source's controller gets a poison counter")
        ));
    }

    private ReaperOfSheoldred(final ReaperOfSheoldred card) {
        super(card);
    }

    @Override
    public ReaperOfSheoldred copy() {
        return new ReaperOfSheoldred(this);
    }
}
