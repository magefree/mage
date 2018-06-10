
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.StaticAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.TetraviteToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author MarcoMarin
 */
public final class Tetravus extends CardImpl {

    public Tetravus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Tetravus enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "{this} enters the battlefield with three +1/+1 counters on it"));
        // At the beginning of your upkeep, you may remove any number of +1/+1 counters from Tetravus. If you do, create that many 1/1 colorless Tetravite artifact creature tokens. They each have flying and "This creature can't be enchanted."
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new TetraviteToken()),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1))), TargetController.YOU, true),
                new SourceHasCounterCondition(CounterType.P1P1, 1), "At the beginning of your upkeep, you may remove any number of +1/+1 counters from Tetravus. If you do, create that many 1/1 colorless Tetravite artifact creature tokens. They each have flying and \"This creature can't be enchanted.\""));
        // At the beginning of your upkeep, you may exile any number of tokens created with Tetravus. If you do, put that many +1/+1 counters on Tetravus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                new ExileTargetCost(new TargetControlledPermanent(new FilterControlledPermanent("Tetravite")))), TargetController.YOU, true));

    }

    public Tetravus(final Tetravus card) {
        super(card);
    }

    @Override
    public Tetravus copy() {
        return new Tetravus(this);
    }
}

class CantBeEnchantedAbility extends StaticAbility {

    public CantBeEnchantedAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public CantBeEnchantedAbility(final CantBeEnchantedAbility ability) {
        super(ability);
    }

    @Override
    public CantBeEnchantedAbility copy() {
        return new CantBeEnchantedAbility(this);
    }

    public boolean canTarget(MageObject source, Game game) {
        if (source.isEnchantment()
                && source.hasSubtype(SubType.AURA, game)) {
            return false;
        }
        return true;
    }

}
