
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SavageThallid extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Fungus");
    
    static {
        filter.add(SubType.FUNGUS.getPredicate());
    }

    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("Saproling");
    
    static {
        filter2.add(SubType.SAPROLING.getPredicate());
    }
    
    public SavageThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a spore counter on Savage Thallid.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));
        // Remove three spore counters from Savage Thallid: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
        // Sacrifice a Saproling: Regenerate target Fungus.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
            new RegenerateTargetEffect(),
            new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filter2, false)));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SavageThallid(final SavageThallid card) {
        super(card);
    }

    @Override
    public SavageThallid copy() {
        return new SavageThallid(this);
    }
}
