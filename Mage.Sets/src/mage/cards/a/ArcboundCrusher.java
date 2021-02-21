
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ModularAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class ArcboundCrusher extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public ArcboundCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Whenever another artifact enters the battlefield, put a +1/+1 counter on Arcbound Crusher.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter));        
        
        // Modular 1
        this.addAbility(new ModularAbility(this, 1));
    }

    private ArcboundCrusher(final ArcboundCrusher card) {
        super(card);
    }

    @Override
    public ArcboundCrusher copy() {
        return new ArcboundCrusher(this);
    }
}
