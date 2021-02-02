
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class ElvishFarmer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Saproling");

    static {
        filter.add(SubType.SAPROLING.getPredicate());
    }

    public ElvishFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a spore counter on Elvish Farmer.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));
        // Remove three spore counters from Elvish Farmer: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
        // Sacrifice a Saproling: You gain 2 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2),
            new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false))));
    }

    private ElvishFarmer(final ElvishFarmer card) {
        super(card);
    }

    @Override
    public ElvishFarmer copy() {
        return new ElvishFarmer(this);
    }
}
