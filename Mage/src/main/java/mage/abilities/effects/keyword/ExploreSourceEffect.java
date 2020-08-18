package mage.abilities.effects.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801, JayDi85
 */
public class ExploreSourceEffect extends OneShotEffect {

    // "it explores. <i>(Reveal the top card of your library. Put that card into
    // your hand if it's a land. Otherwise, put a +1/+1 counter on this creature,
    // then put the card back or put it into your graveyard.)</i>";
    private static final String RULE_TEXT_START = "explores.";
    private static final String RULE_TEXT_HINT = "<i>(Reveal the top card of your library. "
            + "Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on "
            + "this creature, then put the card back or put it into your graveyard.)</i>";
    private static final String RULE_TEXT_AGAIN = ", then %s explores again."; //So far the only card that uses this is Jadelight Ranger

    public static String getRuleText(boolean showAbilityHint) {
        return getRuleText(showAbilityHint, null);
    }

    public static String getRuleText(boolean showAbilityHint, String whosExplores) {
        return getRuleText(showAbilityHint, whosExplores, (byte) 0);
    }

    public static String getRuleText(boolean showAbilityHint, String whosExplores, byte numTimesExplored) {

        String res = whosExplores;
        if (res == null) {
            res = "it";
        }

        if (numTimesExplored > 0) {
            res = String.format(RULE_TEXT_AGAIN, res);
        }
        else {
            res += " " + RULE_TEXT_START;
        }

        if (showAbilityHint) {
            res += " " + RULE_TEXT_HINT;
        }
        return res;
    }

    private String sourceName = "it";
    private boolean showAbilityHint = true;
    private byte numTimesExplored = 0;

    public ExploreSourceEffect() {
        this(true);
    }

    public ExploreSourceEffect(boolean showAbilityHint) {
        this(showAbilityHint, null);
    }

    public ExploreSourceEffect(boolean showAbilityHint, byte numTimesExplored) {
        this(showAbilityHint, null, numTimesExplored);
    }

    public ExploreSourceEffect(boolean showAbilityHint, String whosExplores) {
        this(showAbilityHint, whosExplores, (byte) 0);
    }

    public ExploreSourceEffect(boolean showAbilityHint, String whosExplores, byte numTimesExplored) {
        super(Outcome.Benefit);

        if (whosExplores != null) {
            this.sourceName = whosExplores;
        }
        this.showAbilityHint = showAbilityHint;
        this.numTimesExplored = numTimesExplored;
        setText();
    }

    public ExploreSourceEffect(final ExploreSourceEffect effect) {
        super(effect);
        this.showAbilityHint = effect.showAbilityHint;
        this.sourceName = effect.sourceName;
        this.numTimesExplored = effect.numTimesExplored;
        setText();
    }

    private void setText() {
        this.staticText = getRuleText(this.showAbilityHint, this.sourceName, this.numTimesExplored);
    }

    @Override
    public ExploreSourceEffect copy() {
        return new ExploreSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return explorePermanent(game, source.getSourceId(), source);
    }

    public static boolean explorePermanent(Game game, UUID permanentId, Ability source) {
        Boolean cardWasRevealed = false;
        Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
        if (permanent == null) {
            return false;
        }
        Player permanentController = game.getPlayer(source.getControllerId());
        if (permanentController == null) {
            return false;
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EXPLORED, permanentId,
                source.getSourceId(), permanent.getControllerId()));
        if (permanentController.getLibrary().hasCards()) {
            Card card = permanentController.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            permanentController.revealCards("Explored card", cards, game);
            cardWasRevealed = true;
            if (card != null) {
                if (card.isLand()) {
                    permanentController.moveCards(card, Zone.HAND, source, game);
                } else {
                    if (game.getState().getZone(permanentId) == Zone.BATTLEFIELD) { // needed in case LKI object is used
                        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
                    }
                    if (permanentController.chooseUse(Outcome.Neutral, "Put " + card.getLogName() + " in your graveyard?", source, game)) {
                        permanentController.moveCards(card, Zone.GRAVEYARD, source, game);
                    } else {
                        game.informPlayers(permanentController.getLogName() + " leaves " + card.getLogName() + " on top of their library.");
                    }
                }
            }
        }
        if (!cardWasRevealed
                && game.getState().getZone(permanentId) == Zone.BATTLEFIELD) {
            // If no card is revealed, most likely because that player's library is empty,
            // the exploring creature receives a +1/+1 counter.
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}
