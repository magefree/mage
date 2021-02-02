
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public final class SporesowerThallid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Fungus you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.FUNGUS.getPredicate());
    }

    public SporesowerThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, put a spore counter on each Fungus you control.  
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersAllEffect(CounterType.SPORE.createInstance(), filter), TargetController.YOU, false));
        // Remove three spore counters from Sporesower Thallid: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
    }

    private SporesowerThallid(final SporesowerThallid card) {
        super(card);
    }

    @Override
    public SporesowerThallid copy() {
        return new SporesowerThallid(this);
    }
}
