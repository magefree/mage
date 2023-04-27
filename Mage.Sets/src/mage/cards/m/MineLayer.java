package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MineLayer extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land with a mine counter on it");

    static {
        filter.add(CounterType.MINE.getPredicate());
    }

    public MineLayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}, {tap}: Put a mine counter on target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.MINE.createInstance()), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Whenever a land with a mine counter on it becomes tapped, destroy it.
        this.addAbility(new BecomesTappedTriggeredAbility(new DestroyTargetEffect().setText("destroy that land"), false, filter, true));

        // When Mine Layer leaves the battlefield, remove all mine counters from all lands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RemoveAllMineCountersEffect(), false));
    }

    private MineLayer(final MineLayer card) {
        super(card);
    }

    @Override
    public MineLayer copy() {
        return new MineLayer(this);
    }
}

class RemoveAllMineCountersEffect extends OneShotEffect {

    public RemoveAllMineCountersEffect() {
        super(Outcome.Neutral);
        this.staticText = "remove all mine counters from all lands";
    }

    public RemoveAllMineCountersEffect(final RemoveAllMineCountersEffect effect) {
        super(effect);
    }

    @Override
    public RemoveAllMineCountersEffect copy() {
        return new RemoveAllMineCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.LAND, game)) {
            if (permanent != null) {
                int numToRemove = permanent.getCounters(game).getCount(CounterType.MINE);
                if (numToRemove > 0) {
                    permanent.removeCounters(CounterType.MINE.getName(), numToRemove, source, game);
                }
            }
        }
        return true;
    }
}
