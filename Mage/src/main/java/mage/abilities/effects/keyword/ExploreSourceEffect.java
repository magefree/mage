
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

    // "it explores. <i>(Reveal the top card of your library. Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on this creature, then put the card back or put it into your graveyard.)</i>";
    private static final String RULE_TEXT_START = "explores.";
    private static final String RULE_TEXT_HINT = "<i>(Reveal the top card of your library. Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on this creature, then put the card back or put it into your graveyard.)</i>";

    public static String getRuleText(boolean showAbilityHint) {
        return getRuleText(showAbilityHint, null);
    }
    public static String getRuleText(boolean showAbilityHint, String whosExplores) {

        String res = whosExplores;
        if(res == null){ res = "it"; }

        res +=  " " + RULE_TEXT_START;

        if (showAbilityHint) {
            res += " " + RULE_TEXT_HINT;
        }
        return res;
    }

    private String sourceName = "it";
    private boolean showAbilityHint = true;

    public ExploreSourceEffect() {
        this(true);
    }

    public ExploreSourceEffect(boolean showAbilityHint) {
        this(showAbilityHint, null);
    }

    public ExploreSourceEffect(boolean showAbilityHint, String whosExplores) {
        super(Outcome.Benefit);

        if(whosExplores != null) {
            this.sourceName = whosExplores;
        }
        setText();
    }

    public ExploreSourceEffect(final ExploreSourceEffect effect) {
        super(effect);
        this.showAbilityHint = effect.showAbilityHint;
        this.sourceName = effect.sourceName;
        setText();
    }

    private void setText(){
        this.staticText = getRuleText(this.showAbilityHint, this.sourceName);
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
        Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
        if (permanent == null) {
            return false;
        }
        Player permanentController = game.getPlayer(source.getControllerId());
        if (permanentController == null) {
            return false;
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EXPLORED, permanentId, source.getSourceId(), permanent.getControllerId()));
        if (permanentController.getLibrary().hasCards()) {
            Card card = permanentController.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            permanentController.revealCards("Explored card", cards, game);

            if (card != null) {
                if (card.isLand()) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                } else {
                    permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
                    if (permanentController.chooseUse(Outcome.Neutral, "Put " + card.getLogName() + " in your graveyard?", source, game)) {
                        card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        game.informPlayers(permanentController.getLogName() + " puts " + card.getLogName() + " into their graveyard.");
                    } else {
                        game.informPlayers(permanentController.getLogName() + " leaves " + card.getLogName() + " on top of their library.");
                    }
                }
            }
        }
        return true;
    }
}
