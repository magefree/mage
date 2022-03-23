package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

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
 * @author LevelX2
 */
public class DevourEffect extends ReplacementEffectImpl {

    private final DevourFactor devourFactor;

    public DevourEffect(DevourFactor devourFactor) {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.devourFactor = devourFactor;
    }

    public DevourEffect(final DevourEffect effect) {
        super(effect);
        this.devourFactor = effect.devourFactor;
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
        Target target = new TargetControlledPermanent(1, Integer.MAX_VALUE, devourFactor.getFilter(), true);
        target.setRequired(false);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        if (!controller.chooseUse(Outcome.Detriment, "Devour " + devourFactor.getCardType().toString().toLowerCase() + "s?", source, game)) {
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
        if (!game.isSimulation()) {
            game.informPlayers(creature.getLogName() + " devours " + devouredCreatures + " " + devourFactor.getCardType().toString().toLowerCase() + "s");
        }
        game.getState().processAction(game); // need for multistep effects

        int amountCounters;
        if (devourFactor == DevourFactor.DevourX) {
            amountCounters = devouredCreatures * devouredCreatures;
        } else {
            amountCounters = devouredCreatures * devourFactor.getFactor();
        }
        creature.addCounters(CounterType.P1P1.createInstance(amountCounters), source.getControllerId(), source, game);
        game.getState().setValue(creature.getId().toString() + "devoured", creaturesDevoured);
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder(devourFactor.toString());
        sb.append(" <i>(As this enters the battlefield, you may sacrifice any number of ");
        sb.append(devourFactor.getCardType());
        sb.append("s. This creature enters the battlefield with ");
        sb.append(devourFactor.getRuleText());
        sb.append(")</i>");
        return sb.toString();
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

    public enum DevourFactor {

        Devour1("Devour 1", "that many +1/+1 counters on it", 1),
        Devour2("Devour 2", "twice that many +1/+1 counters on it", 2),
        Devour3("Devour 3", "three times that many +1/+1 counters on it", 3),
        DevourArtifact1("Devour artifact 1", "that many +1/+1 counters on it", 1, CardType.ARTIFACT),
        DevourX("Devour X, where X is the number of creatures devoured this way", "X +1/+1 counters on it for each of those creatures", Integer.MAX_VALUE);

        private final String text;
        private final String ruleText;
        private final int factor;
        private final CardType cardType;
        private final FilterControlledPermanent filter;

        DevourFactor(String text, String ruleText, int factor) {
            this(text, ruleText, factor, CardType.CREATURE);
        }

        DevourFactor(String text, String ruleText, int factor, CardType cardType) {
            this.text = text;
            this.ruleText = ruleText;
            this.factor = factor;
            this.cardType = cardType;
            this.filter = makeFilter(cardType);
        }

        @Override
        public String toString() {
            return text;
        }

        public String getRuleText() {
            return ruleText;
        }

        public int getFactor() {
            return factor;
        }

        public CardType getCardType() {
            return cardType;
        }

        public FilterControlledPermanent getFilter() {
            return filter;
        }

        private static final FilterControlledPermanent makeFilter(CardType cardType) {
            FilterControlledPermanent filter = new FilterControlledPermanent(cardType.toString().toLowerCase() + "s to devour");
            filter.add(cardType.getPredicate());
            filter.add(AnotherPredicate.instance);
            return filter;
        }
    }
}
