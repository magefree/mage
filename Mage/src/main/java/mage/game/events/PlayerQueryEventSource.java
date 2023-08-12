package mage.game.events;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.game.permanent.Permanent;
import mage.util.MultiAmountMessage;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerQueryEventSource implements EventSource<PlayerQueryEvent>, Serializable {

    protected final EventDispatcher<PlayerQueryEvent> dispatcher = new EventDispatcher<PlayerQueryEvent>() {
    };

    @Override
    public void addListener(Listener<PlayerQueryEvent> listener) {
        dispatcher.addListener(listener);
    }

    @Override
    public void removeAllListener() {
        dispatcher.removeAllListener();
    }

    public void ask(UUID playerId, String message, Ability source, Map<String, Serializable> options) {
        dispatcher.fireEvent(PlayerQueryEvent.askEvent(playerId, message, source, options));
    }

    public void select(UUID playerId, String message) {
        dispatcher.fireEvent(PlayerQueryEvent.selectEvent(playerId, message));
    }

    public void select(UUID playerId, String message, Map<String, Serializable> options) {
        dispatcher.fireEvent(PlayerQueryEvent.selectEvent(playerId, message, options));
    }

    public void chooseAbility(UUID playerId, String message, String objectName, List<? extends ActivatedAbility> choices) {
        dispatcher.fireEvent(PlayerQueryEvent.chooseAbilityEvent(playerId, message, objectName, choices));
    }

    public void choosePile(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2) {
        dispatcher.fireEvent(PlayerQueryEvent.choosePileEvent(playerId, message, pile1, pile2));
    }

    public void chooseMode(UUID playerId, String message, Map<UUID, String> modes) {
        dispatcher.fireEvent(PlayerQueryEvent.chooseModeEvent(playerId, message, modes));
    }

    public void target(UUID playerId, String message, Set<UUID> targets, boolean required) {
        dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, targets, required));
    }

    public void target(UUID playerId, String message, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, targets, required, options));
    }

    public void target(UUID playerId, String message, Cards cards, boolean required, Map<String, Serializable> options) {
        dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, cards, required, options));
    }

    public void target(UUID playerId, String message, List<TriggeredAbility> abilities) {
        dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, abilities));
    }

    public void target(UUID playerId, String message, List<Permanent> perms, boolean required) {
        dispatcher.fireEvent(PlayerQueryEvent.targetEvent(playerId, message, perms, required));
    }

    public void playMana(UUID playerId, String message, Map<String, Serializable> options) {
        dispatcher.fireEvent(PlayerQueryEvent.playManaEvent(playerId, message, options));
    }

    public void amount(UUID playerId, String message, int min, int max) {
        dispatcher.fireEvent(PlayerQueryEvent.amountEvent(playerId, message, min, max));
    }

    public void multiAmount(UUID playerId, List<MultiAmountMessage> messages, int min, int max,
            Map<String, Serializable> options) {
        dispatcher.fireEvent(PlayerQueryEvent.multiAmountEvent(playerId, messages, min, max, options));
    }

    public void chooseChoice(UUID playerId, Choice choice) {
        dispatcher.fireEvent(PlayerQueryEvent.chooseChoiceEvent(playerId, choice));
    }

    public void playXMana(UUID playerId, String message) {
        dispatcher.fireEvent(PlayerQueryEvent.playXManaEvent(playerId, message));
    }

    public void pickCard(UUID playerId, String message, List<Card> booster, int time) {
        dispatcher.fireEvent(PlayerQueryEvent.pickCard(playerId, message, booster, time));
    }

    public void construct(UUID playerId, String message, int time) {
        dispatcher.fireEvent(PlayerQueryEvent.construct(playerId, message, time));
    }

    public void informPlayer(UUID playerId, String message) {
        dispatcher.fireEvent(PlayerQueryEvent.informPersonal(playerId, message));
    }

}
