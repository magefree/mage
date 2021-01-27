package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ContagionEngine extends CardImpl {

    public ContagionEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // When Contagion Engine enters the battlefield, put a -1/-1 counter on each creature target player controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ContagionEngineEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {4}, {T}: Proliferate, then proliferate again. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there. Then do it again.)
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProliferateEffect("", false), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ProliferateEffect(" again", true).concatBy(", then"));
        this.addAbility(ability);
    }

    private ContagionEngine(final ContagionEngine card) {
        super(card);
    }

    @Override
    public ContagionEngine copy() {
        return new ContagionEngine(this);
    }

}

class ContagionEngineEffect extends OneShotEffect {

    ContagionEngineEffect() {
        super(Outcome.UnboostCreature);
        staticText = "put a -1/-1 counter on each creature target player controls";
    }

    private ContagionEngineEffect(final ContagionEngineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                creature.addCounters(CounterType.M1M1.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ContagionEngineEffect copy() {
        return new ContagionEngineEffect(this);
    }

}
