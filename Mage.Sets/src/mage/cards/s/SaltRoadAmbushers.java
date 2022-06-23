
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class SaltRoadAmbushers extends CardImpl {

    public SaltRoadAmbushers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another permanent you control is turned face up, if it's a creature, put two +1/+1 counters on it.
        this.addAbility(new SaltRoadAmbushersTriggeredAbility());
        
        // Megamorph {3}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{G}{G}"), true));
    }

    private SaltRoadAmbushers(final SaltRoadAmbushers card) {
        super(card);
    }

    @Override
    public SaltRoadAmbushers copy() {
        return new SaltRoadAmbushers(this);
    }
}

class SaltRoadAmbushersTriggeredAbility extends TurnedFaceUpAllTriggeredAbility {
    
    
private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public SaltRoadAmbushersTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), filter, true);
    }

    public SaltRoadAmbushersTriggeredAbility(final SaltRoadAmbushersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SaltRoadAmbushersTriggeredAbility copy() {
        return new SaltRoadAmbushersTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another permanent you control is turned face up, if it's a creature, put two +1/+1 counters on it.";
    }
}