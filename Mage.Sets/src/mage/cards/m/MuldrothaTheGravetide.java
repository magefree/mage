
package mage.cards.m;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
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
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MuldrothaTheGravetideCastFromGraveyardEffect())
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

    public MuldrothaTheGravetideCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit, true);
        staticText = "During each of your turns, you may play a land and cast a permanent spell of each permanent type from your graveyard. "
                + "<i>(If a card has multiple permanent types, choose one as you play it.)</i>";
    }

    public MuldrothaTheGravetideCastFromGraveyardEffect(final MuldrothaTheGravetideCastFromGraveyardEffect effect) {
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
            MageObject mageObject = game.getObject(objectId);
            if (mageObject != null && watcher != null) {
                for (CardType cardType : mageObject.getCardType(game)) {
                    if (cardType.isPermanentType()) {
                        MageObjectReference mor = new MageObjectReference(source.getSourceObject(game), game);
                        if (!watcher.permanentTypePlayedFromGraveyard(mor, cardType)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

/**
 * Holds track of the consumed types of each permission giving source
 *
 * @author LevelX2
 */
class MuldrothaTheGravetideWatcher extends Watcher {

    private final HashMap<MageObjectReference, Set<CardType>> sourcePlayedPermanentTypes = new HashMap<>(); // source that played permanent types from graveyard
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
            addPermanentTypes(event, game.getPermanentOrLKIBattlefield(event.getTargetId()), game);
        }

        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.getFromZone() == Zone.GRAVEYARD) {
                addPermanentTypes(event, spell, game);
            }
        }
    }

    private void addPermanentTypes(GameEvent event, Card mageObject, Game game) {
        if (mageObject != null 
                && event.getAdditionalReference() != null 
                && MageIdentifier.MuldrothaTheGravetideWatcher.equals(event.getAdditionalReference().getApprovingAbility().getIdentifier())) {
            UUID playerId = null;
            if (mageObject instanceof Spell) {
                playerId = ((Spell) mageObject).getControllerId();
            } else if (mageObject instanceof Permanent) {
                playerId = ((Permanent) mageObject).getControllerId();
            }
            if (playerId != null) {
                Set<CardType> permanentTypes = sourcePlayedPermanentTypes.get(event.getAdditionalReference().getApprovingMageObjectReference());
                if (permanentTypes == null) {
                    permanentTypes = EnumSet.noneOf(CardType.class);
                    sourcePlayedPermanentTypes.put(event.getAdditionalReference().getApprovingMageObjectReference(), permanentTypes);
                }
                Set<CardType> typesNotCast = EnumSet.noneOf(CardType.class);
                for (CardType cardType : mageObject.getCardType(game)) {
                    if (cardType.isPermanentType()) {
                        if (!permanentTypes.contains(cardType)) {
                            typesNotCast.add(cardType);
                        }
                    }
                }
                if (typesNotCast.size() <= 1) {
                    permanentTypes.addAll(typesNotCast);
                } else {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        Choice typeChoice = new ChoiceImpl(true);
                        typeChoice.setMessage("Choose permanent type you consume for casting from graveyard.");
                        for (CardType cardType : typesNotCast) {
                            typeChoice.getChoices().add(cardType.toString());
                        }
                        if (player.choose(Outcome.Detriment, typeChoice, game)) {
                            String typeName = typeChoice.getChoice();
                            CardType chosenType = null;
                            for (CardType cardType : CardType.values()) {
                                if (cardType.toString().equals(typeName)) {
                                    chosenType = cardType;
                                    break;
                                }
                            }
                            if (chosenType != null) {
                                permanentTypes.add(chosenType);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        sourcePlayedPermanentTypes.clear();
        super.reset();
    }

    public boolean permanentTypePlayedFromGraveyard(MageObjectReference sourceMor, CardType cardType) {
        Set<CardType> permanentTypes = sourcePlayedPermanentTypes.get(sourceMor);
        if (permanentTypes != null) {
            return permanentTypes.contains(cardType);
        }
        return false;
    }

}
