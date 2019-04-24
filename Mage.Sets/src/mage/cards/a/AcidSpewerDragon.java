
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class AcidSpewerDragon extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other Dragon creature you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public AcidSpewerDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        
        // Megamorph {5}{B}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{5}{B}{B}"), true));
        
        // When Acid-Spewer Dragon is turned face up, put a +1/+1 counter on each other Dragon creature you control.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false, false));
    }

    public AcidSpewerDragon(final AcidSpewerDragon card) {
        super(card);
    }

    @Override
    public AcidSpewerDragon copy() {
        return new AcidSpewerDragon(this);
    }
}
