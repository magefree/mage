package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * 10/4/2004: If the creature regenerates, the fuse counters are still removed and
 * the four damage is still dealt.
 * 10/4/2004: If there are two Bomb Squads on the battlefield when a creature ends
 * up with 4 or more fuse counters, both Bomb Squad abilities will trigger
 * causing 4 damage each even though the first to resolve will destroy the
 * creature.
 *
 * @author LevelX2
 */
public final class BombSquad extends CardImpl {

    public BombSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.DWARF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Put a fuse counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.FUSE.createInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // At the beginning of your upkeep, put a fuse counter on each creature with a fuse counter on it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new BombSquadBeginningEffect(), TargetController.YOU, false));
        // Whenever a creature has four or more fuse counters on it, remove all fuse counters from it and destroy it. That creature deals 4 damage to its controller.
        this.addAbility(new BombSquadTriggeredAbility());
    }

    private BombSquad(final BombSquad card) {
        super(card);
    }

    @Override
    public BombSquad copy() {
        return new BombSquad(this);
    }
}

class BombSquadTriggeredAbility extends TriggeredAbilityImpl {

    public BombSquadTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BombSquadDamgeEffect(), false);
        setTriggerPhrase("Whenever a creature has four or more fuse counters on it, ");
    }

    public BombSquadTriggeredAbility(final BombSquadTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BombSquadTriggeredAbility copy() {
        return new BombSquadTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.FUSE.getName())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (StaticFilters.FILTER_PERMANENT_CREATURE.match(permanent, game)) {
                if (4 <= permanent.getCounters(game).getCount(CounterType.FUSE)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

class BombSquadDamgeEffect extends OneShotEffect {

    public BombSquadDamgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove all fuse counters from it and destroy it. That creature deals 4 damage to its controller";
    }

    public BombSquadDamgeEffect(final BombSquadDamgeEffect effect) {
        super(effect);
    }

    @Override
    public BombSquadDamgeEffect copy() {
        return new BombSquadDamgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature != null) {
            creature.removeCounters(CounterType.FUSE.getName(), creature.getCounters(game).getCount(CounterType.FUSE), source, game);
            creature.destroy(source, game, false);
        }
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.damage(4, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}


class BombSquadBeginningEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a fuse counter on it");

    static {
        filter.add(CounterType.FUSE.getPredicate());
    }

    public BombSquadBeginningEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a fuse counter on each creature with a fuse counter on it";
    }

    public BombSquadBeginningEffect(final BombSquadBeginningEffect effect) {
        super(effect);
    }

    @Override
    public BombSquadBeginningEffect copy() {
        return new BombSquadBeginningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.addCounters(CounterType.FUSE.createInstance(), source.getControllerId(), source, game);

            game.informPlayers(card.getName() + " puts a fuse counter on " + permanent.getName());
        }
        return true;
    }
}
