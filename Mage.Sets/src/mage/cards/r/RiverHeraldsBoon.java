package mage.cards.r;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RiverHeraldsBoon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MERFOLK, "Merfolk");

    public RiverHeraldsBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put a +1/+1 counter on target creature and a +1/+1 counter on up to one target Merfolk.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setTargetPointer(new SecondTargetPointer()).setText("and a +1/+1 counter on up to one target Merfolk"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));
    }

    private RiverHeraldsBoon(final RiverHeraldsBoon card) {
        super(card);
    }

    @Override
    public RiverHeraldsBoon copy() {
        return new RiverHeraldsBoon(this);
    }
}
