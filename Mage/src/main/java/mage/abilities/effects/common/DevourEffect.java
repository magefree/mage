package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Effect for the DevourAbility
 * <p>
 * 702.81. Devour 702.81a Devour is a static ability. "Devour N" means "As this
 * object enters the battlefield, you may sacrifice any number of creatures.
 * This permanent enters the battlefield with N +1/+1 counters on it for each
 * creature sacrificed this way." 702.81b Some objects have abilities that refer
 * to the number of creatures the permanent devoured. "It devoured" means
 * "sacrificed as a result of its devour ability as it entered the battlefield."
 *
 * @author LevelX2, Susucr
 */
public class DevourEffect extends ReplacementEffectImpl {

    // how many counters per devoured permanent.
    // Integer.MAX_VALUE is a special value that means "X, where X is the number of devoured permanent"
    private final int devourFactor;
    // For text generation, the filter's message is expected to be the singular
    // type word in the devour ability. e.g. "Food" "artifact" "creature".
    // "creature" is a special case as the rule will not mention it.
    //
    // 's' will be added to pluralize, so far so good with the current text generation.
    private final FilterControlledPermanent filterDevoured;

    public DevourEffect(int devourFactor, FilterControlledPermanent filterDevoured) {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.devourFactor = devourFactor;
        this.filterDevoured = filterDevoured;
    }

    private DevourEffect(final DevourEffect effect) {
        super(effect);
        this.devourFactor = effect.devourFactor;
        this.filterDevoured = effect.filterDevoured;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
            game.getState().setValue(sourcePermanent.getId().toString() + "devoured", null);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature == null || controller == null) {
            return false;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent(filterDevoured.getMessage() + "s to devour");
        for (Predicate predicate : filterDevoured.getPredicates()) {
            filter.add(predicate);
        }
        filter.add(AnotherPredicate.instance);

        Target target = new TargetControlledPermanent(1, Integer.MAX_VALUE, filter, true);
        target.setRequired(false);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        if (!controller.chooseUse(Outcome.Detriment, "Devour " + filterDevoured.getMessage() + "s?", source, game)) {
            return false;
        }
        controller.chooseTarget(Outcome.Detriment, target, source, game);
        if (target.getTargets().isEmpty()) {
            return false;
        }
        List<Permanent> creaturesDevoured = new ArrayList<>();
        int devouredCreatures = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent targetCreature = game.getPermanent(targetId);
            if (targetCreature != null && targetCreature.sacrifice(source, game)) {
                creaturesDevoured.add(targetCreature);
                devouredCreatures++;
            }
        }

        game.informPlayers(creature.getLogName()
                + " devours " + devouredCreatures + " "
                + filterDevoured.getMessage() + (devouredCreatures > 1 ? "s" : "")
        );
        
        game.getState().processAction(game); // need for multistep effects

        int amountCounters;
        if (devourFactor == Integer.MAX_VALUE) {
            amountCounters = devouredCreatures * devouredCreatures;
        } else {
            amountCounters = devouredCreatures * devourFactor;
        }
        creature.addCounters(CounterType.P1P1.createInstance(amountCounters), source.getControllerId(), source, game);
        game.getState().setValue(creature.getId().toString() + "devoured", creaturesDevoured);
        return false;
    }

    @Override
    public String getText(Mode mode) {
        String text = "Devour ";

        String filterMessage = filterDevoured.getMessage();
        if (!filterMessage.equals("creature")) {
            text += filterMessage + " ";
        }

        if (devourFactor == Integer.MAX_VALUE) {
            text += "X, where X is the number of " + filterMessage + "s devoured this way";
        } else {
            text += devourFactor;
        }

        text += " <i>(As this enters the battlefield, you may sacrifice any number of "
                + filterMessage + "s. "
                + "This creature enters the battlefield with ";

        if (devourFactor == Integer.MAX_VALUE) {
            text += "X +1/+1 counters on it for each of those creatures";
        } else {
            if (devourFactor == 2) {
                text += "twice ";
            } else if (devourFactor > 2) {
                text += CardUtil.numberToText(devourFactor) + " times ";
            }

            text += "that many +1/+1 counters on it";
        }

        text += ".)</i>";

        return text;
    }

    public List<Permanent> getDevouredCreatures(Game game, UUID permanentId) {
        Object object = game.getState().getValue(permanentId.toString() + "devoured");
        if (object != null) {
            return (List<Permanent>) object;
        }
        return Collections.emptyList();
    }

    public int getDevouredCreaturesAmount(Game game, UUID permanentId) {
        Object object = game.getState().getValue(permanentId.toString() + "devoured");
        if (object != null) {
            return ((List<Permanent>) object).size();
        }
        return 0;
    }

    @Override
    public DevourEffect copy() {
        return new DevourEffect(this);
    }
}
