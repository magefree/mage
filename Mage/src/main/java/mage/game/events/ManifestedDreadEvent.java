package mage.game.events;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Cards;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class ManifestedDreadEvent extends GameEvent {

    private final List<MageObjectReference> graveyardCards = new ArrayList<>();

    public ManifestedDreadEvent(Permanent permanent, Ability source, UUID playerId, Cards cards, Game game) {
        super(EventType.MANIFESTED_DREAD, permanent == null ? null : permanent.getId(), source, playerId);
        cards.getCards(game)
                .stream()
                .map(card -> new MageObjectReference(card, game))
                .forEach(graveyardCards::add);
    }

    public List<MageObjectReference> getGraveyardCards() {
        return graveyardCards;
    }
}
