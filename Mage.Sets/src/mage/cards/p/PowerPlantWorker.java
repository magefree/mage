package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class PowerPlantWorker extends CardImpl {

    public PowerPlantWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}: Power Plant Worker gets +2/+2 until end of turn. If you control creatures named Mine Worker and Tower Worker, put two +1/+1 counters on Power Plant Worker instead. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new PowerPlantWorkerEffect(), new GenericManaCost(3)));
    }

    private PowerPlantWorker(final PowerPlantWorker card) {
        super(card);
    }

    @Override
    public PowerPlantWorker copy() {
        return new PowerPlantWorker(this);
    }
}

class PowerPlantWorkerEffect extends OneShotEffect {

    public PowerPlantWorkerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +2/+2 until end of turn. If you control creatures named Mine Worker and Tower Worker, put two +1/+1 counters on {this} instead.";
    }

    private PowerPlantWorkerEffect(final PowerPlantWorkerEffect effect) {
        super(effect);
    }

    @Override
    public PowerPlantWorkerEffect copy() {
        return new PowerPlantWorkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            return false;
        }
        String mineName = "Mine Worker";
        String towerName = "Tower Worker";
        boolean mine = false;
        boolean tower = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            String name = permanent.getName();
            if (!mine && mineName.equals(name)) {
                mine = true;
            } else if (!tower && towerName.equals(name)) {
                tower = true;
            }
            if (mine && tower) {
                return sourcePermanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
            }
        }
        game.addEffect(new BoostSourceEffect(2, 2, Duration.EndOfTurn), source);
        return true;
    }
}
