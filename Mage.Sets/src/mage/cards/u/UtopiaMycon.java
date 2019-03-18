
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class UtopiaMycon extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Saproling");
    
    static {
        filter.add(new SubtypePredicate(SubType.SAPROLING));
    }

    public UtopiaMycon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a spore counter on Utopia Mycon.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));
        
        // Remove three spore counters from Utopia Mycon: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
        
        // Sacrifice a Saproling: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filter, false)));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public UtopiaMycon(final UtopiaMycon card) {
        super(card);
    }

    @Override
    public UtopiaMycon copy() {
        return new UtopiaMycon(this);
    }
}
