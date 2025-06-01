package mage.cards.n;

import mage.MageInt;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValueEqualToCountersSourceCountPredicate;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class Necroplasm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(
            "each creature with mana value equal to the number of +1/+1 counters on {this}"
    );
    static {
        filter.add(new ManaValueEqualToCountersSourceCountPredicate(CounterType.P1P1));
    }

    public Necroplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a +1/+1 counter on Necroplasm.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
        
        // At the beginning of your end step, destroy each creature with converted mana cost equal to the number of +1/+1 counters on Necroplasm.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DestroyAllEffect(filter)));
        
        // Dredge 2
        this.addAbility(new DredgeAbility(2));
    }

    private Necroplasm(final Necroplasm card) {
        super(card);
    }

    @Override
    public Necroplasm copy() {
        return new Necroplasm(this);
    }
}
