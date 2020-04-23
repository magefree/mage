package mage.cards.l;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavabrinkFloodgates extends CardImpl {

    public LavabrinkFloodgates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        // {T}: Add {R}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new TapSourceCost()));

        // At the beginning of each player's upkeep, that player may put a doom counter on Lavabrink Floodgates or remove a doom counter from it. Then if it has three or more doom counters on it, sacrifice it. When you do, it deals 6 damage to each creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new LavabrinkFloodgatesEffect(), TargetController.ACTIVE, false
        ));
    }

    private LavabrinkFloodgates(final LavabrinkFloodgates card) {
        super(card);
    }

    @Override
    public LavabrinkFloodgates copy() {
        return new LavabrinkFloodgates(this);
    }
}

class LavabrinkFloodgatesEffect extends OneShotEffect {

    LavabrinkFloodgatesEffect() {
        super(Outcome.Benefit);
        staticText = "that player may put a doom counter on {this} or remove a doom counter from it. " +
                "Then if it has three or more doom counters on it, sacrifice it. " +
                "When you do, it deals 6 damage to each creature.";
    }

    private LavabrinkFloodgatesEffect(final LavabrinkFloodgatesEffect effect) {
        super(effect);
    }

    @Override
    public LavabrinkFloodgatesEffect copy() {
        return new LavabrinkFloodgatesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl();
        choice.setChoices(new HashSet(Arrays.asList(
                "Add a doom counter",
                "Remove a doom counter",
                "Do nothing"
        )));
        player.choose(outcome, choice, game);
        switch (choice.getChoice()) {
            case "Add a doom counter":
                permanent.addCounters(CounterType.DOOM.createInstance(), source, game);
                break;
            case "Remove a doom counter":
                permanent.removeCounters(CounterType.DOOM.createInstance(), game);
                break;
            case "Do nothing":
            default:
                return false;
        }
        if (permanent.getCounters(game).getCount(CounterType.DOOM) < 3
                || !permanent.sacrifice(source.getSourceId(), game)) {
            return true;
        }
        game.addDelayedTriggeredAbility(new LavabrinkFloodgatesReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class LavabrinkFloodgatesReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    LavabrinkFloodgatesReflexiveTriggeredAbility() {
        super(new DamageAllEffect(6, StaticFilters.FILTER_PERMANENT_CREATURE), Duration.OneUse, true);
    }

    private LavabrinkFloodgatesReflexiveTriggeredAbility(final LavabrinkFloodgatesReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LavabrinkFloodgatesReflexiveTriggeredAbility copy() {
        return new LavabrinkFloodgatesReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "It deals 6 damage to each creature.";
    }
}
