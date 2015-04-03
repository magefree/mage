/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * Effect for the AmplifyAbility
 *
 *      702.37. Amplify 
 *        702.37a Amplify is a static ability. “Amplify N” means “As
 *          this object enters the battlefield, reveal any number of cards from your hand
 *          that share a creature type with it. This permanent enters the battlefield
 *          with N +1/+1 counters on it for each card revealed this way. You can’t reveal
 *          this card or any other cards that are entering the battlefield at the same
 *          time as this card.” 
 *        702.37b If a creature has multiple instances of amplify,
 *          each one works separately.
 *
 *
 * @author FenrisulfrX
 */
public class AmplifyEffect extends ReplacementEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creatures cards to reveal");

    private final AmplifyFactor amplifyFactor;

    public enum AmplifyFactor {
        Amplify1("Amplify 1", "put one +1/+1 counters on it", 1),
        Amplify2("Amplify 2", "put two +1/+1 counters on it", 2),
        Amplify3("Amplify 3", "put three +1/+1 counters on it", 3);

        private final String text;
        private final String ruleText;
        private final int factor;

        AmplifyFactor(String text, String ruleText, int factor) {
            this.text = text;
            this.ruleText = ruleText;
            this.factor = factor;
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
    }

    public AmplifyEffect(AmplifyFactor amplifyFactor) {
        super(Duration.EndOfGame, Outcome.Benefit);
        this.amplifyFactor = amplifyFactor;
    }

    public AmplifyEffect(final AmplifyEffect effect) {
        super(effect);
        this.amplifyFactor = effect.amplifyFactor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            game.getState().setValue(sourcePermanent.getId().toString() + "revealed", null);
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
        Permanent creature = game.getPermanent(event.getTargetId());
        Player controller = game.getPlayer(creature.getControllerId());
        if (controller != null) {
            for (String subtype : creature.getSubtype()) {
                filter.add(new SubtypePredicate((subtype)));
            }
            if (controller.getHand().count(filter, source.getSourceId(), source.getControllerId(), game) > 0){
                if (controller.chooseUse(Outcome.Benefit, "Reveal cards?", game)) {
                    TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
                    if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                        Cards cards = new CardsImpl();
                        for (UUID uuid : target.getTargets()) {
                            cards.add(controller.getHand().get(uuid, game));
                        }
                        int amountCounters = cards.size() * amplifyFactor.getFactor();
                        creature.addCounters(CounterType.P1P1.createInstance(amountCounters), game);
                        controller.revealCards("cards", cards, game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder(amplifyFactor.toString());
        sb.append("<i>(As this enter the battlefield, ");
        sb.append(amplifyFactor.getRuleText()).append(" for each card" +
                " you reveal that shares a type with it in your hand.)</i>");
        return sb.toString();
    }
    
    @Override
    public AmplifyEffect copy() {
        return new AmplifyEffect(this);
    }
}
