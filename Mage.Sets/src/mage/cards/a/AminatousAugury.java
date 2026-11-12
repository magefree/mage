package mage.cards.a;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.assignment.RoleAssignment;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author credman0
 */
public class AminatousAugury extends CardImpl {

    public AminatousAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}{U}");

        // Exile the top eight cards of your library.
        // You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a card of that type from among the exiled cards
        // without paying its mana cost.
        this.getSpellAbility().addEffect(new AminatousAuguryEffect());
        this.getSpellAbility().addWatcher(new AminatousAuguryWatcher());
    }

    private AminatousAugury(final AminatousAugury card) {
        super(card);
    }

    @Override
    public AminatousAugury copy() {
        return new AminatousAugury(this);
    }

}

class AminatousAuguryEffect extends OneShotEffect {

    AminatousAuguryEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile the top eight cards of your library. "
                + "You may put a land card from among them onto the battlefield. "
                + "Until end of turn, for each nonland card type, "
                + "you may cast a spell of that type from among the exiled cards without paying its mana cost.";
    }

    private AminatousAuguryEffect(final AminatousAuguryEffect effect) {
        super(effect);
    }

    @Override
    public AminatousAuguryEffect copy() {
        return new AminatousAuguryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        // move cards from library to exile
        game.getExile().createZone(CardUtil.getExileZoneId(game, source), CardUtil.createObjectRelatedWindowTitle(source, game, null)).setCleanupOnEndTurn(true);
        controller.moveCardsToExile(controller.getLibrary().getTopCards(game, 8), source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.createObjectRelatedWindowTitle(source, game, null));
        ExileZone auguryExileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (auguryExileZone == null) {
            return true;
        }

        Cards cardsToCast = new CardsImpl();
        cardsToCast.addAllCards(auguryExileZone.getCards(game));

        // put a land card from among them onto the battlefield
        TargetCard target = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_LAND_A);

        if (cardsToCast.count(StaticFilters.FILTER_CARD_LAND, game) > 0) {
            if (controller.chooseUse(Outcome.PutLandInPlay, "Put a land from among the exiled cards into play?", source, game)) {
                if (controller.choose(Outcome.PutLandInPlay, cardsToCast, target, source, game)) {
                    Card card = cardsToCast.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cardsToCast.remove(card);
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }
        }

        // TODO staticFilters must be configured to check the main card face (Ex: MDFC card like Sea Gate Restoration does not count as a land if face up)
        for (Card card : cardsToCast.getCards(game)) {
            // ex: Sea Gate Restoration bug #9956
            if (card instanceof ModalDoubleFacedCard) {
                ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
                if (leftHalfCard != null
                        && !leftHalfCard.isLand(game)) {
                    AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
                continue;
            }
            if (!card.isLand(game)) {
                AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}

class AminatousAuguryCastFromExileEffect extends AsThoughEffectImpl {

    AminatousAuguryCastFromExileEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
        staticText = "Cast this card without paying its mana cost";
    }

    private AminatousAuguryCastFromExileEffect(final AminatousAuguryCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AminatousAuguryCastFromExileEffect copy() {
        return new AminatousAuguryCastFromExileEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        AminatousAuguryWatcher.addPlayable(source, game);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }

        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        List<Set<CardType>> castCardTypes = AminatousAuguryWatcher.getCastCardTypes(source, game);

        if (objectId == null || !CardUtil.getMainCardId(game, objectId).equals(getTargetPointer().getFirst(game, source))) {
            return false;
        }

        if (game.getState().getZone(objectId) != Zone.EXILED) {
            return false;
        }

        Card card = game.getCard(objectId);
        if (card == null) {
            return false;
        }

        // Each spell cast this way needs its own card type, a card with multiple types can be cast as any of them
        List<Set<CardType>> cardRoles = new ArrayList<>(castCardTypes);
        cardRoles.add(EnumSet.copyOf(card.getCardType(game)));
        if (RoleAssignment.getMaxAssignment(cardRoles) < cardRoles.size()) {
            return false;
        }

        allowCardToPlayWithoutMana(objectId, source, player.getId(), game);
        return true;
    }
}

/**
 * Holds track of the card types of the spells cast with each Aminatou's Augury
 */
class AminatousAuguryWatcher extends Watcher {

    private final Map<MageObjectReference, List<Set<CardType>>> morMap = new HashMap<>();

    AminatousAuguryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST || event.getApprovingObject() == null) {
            return;
        }
        List<Set<CardType>> castCardTypes = morMap.get(new MageObjectReference(event.getApprovingObject().getApprovingAbility()));
        Spell spell = game.getSpell(event.getTargetId());
        if (castCardTypes == null || spell == null) {
            return;
        }
        // card types as cast (split half, adventure spell, mdf side), later changes on battlefield don't matter
        castCardTypes.add(EnumSet.copyOf(spell.getCardType(game)));
    }

    @Override
    public void reset() {
        morMap.clear();
        super.reset();
    }

    static void addPlayable(Ability source, Game game) {
        game.getState()
                .getWatcher(AminatousAuguryWatcher.class)
                .morMap
                .computeIfAbsent(new MageObjectReference(source), m -> new ArrayList<>());
    }

    static List<Set<CardType>> getCastCardTypes(Ability source, Game game) {
        return game.getState()
                .getWatcher(AminatousAuguryWatcher.class)
                .morMap
                .getOrDefault(new MageObjectReference(source), Collections.emptyList());
    }
}
