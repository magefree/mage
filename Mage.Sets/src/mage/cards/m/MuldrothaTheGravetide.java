
package mage.cards.m;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.assignment.RoleAssignment;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class MuldrothaTheGravetide extends CardImpl {

    public MuldrothaTheGravetide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // During each of your turns, you may play up to one permanent card of each permanent type from your graveyard.
        this.addAbility(new SimpleStaticAbility(new MuldrothaTheGravetideCastFromGraveyardEffect())
                .setIdentifier(MageIdentifier.MuldrothaTheGravetideWatcher), 
                new MuldrothaTheGravetideWatcher());
    }

    private MuldrothaTheGravetide(final MuldrothaTheGravetide card) {
        super(card);
    }

    @Override
    public MuldrothaTheGravetide copy() {
        return new MuldrothaTheGravetide(this);
    }
}

class MuldrothaTheGravetideCastFromGraveyardEffect extends AsThoughEffectImpl {

    MuldrothaTheGravetideCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During each of your turns, you may play a land and cast a permanent spell of each permanent type from your graveyard. "
                + "<i>(If a card has multiple permanent types, choose one as you play it.)</i>";
    }

    private MuldrothaTheGravetideCastFromGraveyardEffect(final MuldrothaTheGravetideCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MuldrothaTheGravetideCastFromGraveyardEffect copy() {
        return new MuldrothaTheGravetideCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.isControlledBy(affectedControllerId)
                && source.isControlledBy(game.getOwnerId(objectId)) // only from your graveyard
                && affectedControllerId.equals(game.getActivePlayerId()) // only during your turns (e.g. prevent flash creatures)
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            MuldrothaTheGravetideWatcher watcher = game.getState().getWatcher(MuldrothaTheGravetideWatcher.class);
            Card card = game.getCard(objectId);
            if (card != null && watcher != null) {
                MageObjectReference mor = new MageObjectReference(source.getSourceObject(game), game);
                List<Set<CardType>> playedCardTypes = watcher.getPlayedCardTypes(mor);
                if (card.isLand(game)) {
                    // a land card can be played only as a land (305.9)
                    return playedCardTypes.stream().noneMatch(cardTypes -> cardTypes.contains(CardType.LAND));
                }
                // each spell cast this way needs its own permanent type, a card with multiple types can be cast as any of them
                List<Set<CardType>> cardRoles = new ArrayList<>(playedCardTypes);
                cardRoles.add(card.getCardType(game).stream().filter(CardType::isPermanentType).collect(Collectors.toSet()));
                return RoleAssignment.getMaxAssignment(cardRoles) == cardRoles.size();
            }
        }
        return false;
    }
}

/**
 * Holds track of the permanent types of the cards played with each permission giving source
 *
 * @author LevelX2
 */
class MuldrothaTheGravetideWatcher extends Watcher {

    private final HashMap<MageObjectReference, List<Set<CardType>>> sourcePlayedCardTypes = new HashMap<>(); // source that played cards from graveyard
    // final HashMap<UUID, Set<CardType>> playerPlayedPermanentTypes = new HashMap<>(); // player that played permanent types from graveyard
    // 4/27/2018 If multiple effects allow you to play a card from your graveyard, such as those of Gisa and Geralf and Karador,
    // Ghost Chieftain, you must announce which permission you're using as you begin to play the card.
    // 4/27/2018: If you play a card from your graveyard and then have a new Muldrotha come under your control in the same turn,
    // you may play another card of that type from your graveyard that turn.
    private Zone fromZone;

    public MuldrothaTheGravetideWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND) {
            fromZone = game.getState().getZone(event.getTargetId()); // Remember the Zone the land came from
        }
        if (event.getType() == GameEvent.EventType.LAND_PLAYED && fromZone == Zone.GRAVEYARD) {
            addPlayedCard(event, game.getPermanentOrLKIBattlefield(event.getTargetId()), game);
        }

        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.getFromZone() == Zone.GRAVEYARD) {
                addPlayedCard(event, spell, game);
            }
        }
    }

    private void addPlayedCard(GameEvent event, Card card, Game game) {
        if (card != null
                && event.getApprovingObject() != null
                && MageIdentifier.MuldrothaTheGravetideWatcher.equals(event.getApprovingObject().getApprovingAbility().getIdentifier())) {
            // card types as played or cast (split half, adventure spell, mdf side), later changes on battlefield don't matter
            sourcePlayedCardTypes
                    .computeIfAbsent(event.getApprovingObject().getApprovingMageObjectReference(), m -> new ArrayList<>())
                    .add(card.getCardType(game).stream().filter(CardType::isPermanentType).collect(Collectors.toSet()));
        }
    }

    @Override
    public void reset() {
        sourcePlayedCardTypes.clear();
        super.reset();
    }

    public List<Set<CardType>> getPlayedCardTypes(MageObjectReference sourceMor) {
        return sourcePlayedCardTypes.getOrDefault(sourceMor, Collections.emptyList());
    }

}
