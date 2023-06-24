
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class LukeSkywalker extends CardImpl {

    public LukeSkywalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever another creature enters the battlefield under your conrol, put a +1/+1 counter on Luke Skywalker.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new FilterControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));

        // Remove all +1/+1 counters from Luke Skywalker: Luke gains hexproof until end of turn. Activate this ability only if at least one +1/+1 counter is removed this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn), new LukeSkywalkerCost()));
    }

    private LukeSkywalker(final LukeSkywalker card) {
        super(card);
    }

    @Override
    public LukeSkywalker copy() {
        return new LukeSkywalker(this);
    }
}

class LukeSkywalkerCost extends CostImpl {

    public LukeSkywalkerCost() {
        super();
        this.text = "Remove all +1/+1 counters from {this}";
    }

    public LukeSkywalkerCost(LukeSkywalkerCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            return (permanent.getCounters(game).getCount(CounterType.P1P1)) > 0;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            int countersCount = permanent.getCounters(game).getCount(CounterType.P1P1);
            permanent.removeCounters(CounterType.P1P1.createInstance(countersCount), source, game);
            this.paid = true;
            return true;
        }
        return false;
    }

    @Override
    public LukeSkywalkerCost copy() {
        return new LukeSkywalkerCost(this);
    }

}
