package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThiefOfSanity extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public ThiefOfSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Thief of Sanity deals combat damage to a player, look at the top three cards of that player's library, exile one of them face down, then put the rest into their graveyard. For as long as that card remains exiled, you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ThiefOfSanityEffect(), false, true));
    }

    private ThiefOfSanity(final ThiefOfSanity card) {
        super(card);
    }

    @Override
    public ThiefOfSanity copy() {
        return new ThiefOfSanity(this);
    }
}

class ThiefOfSanityEffect extends OneShotEffect {

    public ThiefOfSanityEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top three cards of that player's library, exile one of them face down, then put the rest into their graveyard. "
                + "For as long as that card remains exiled, you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it";
    }

    private ThiefOfSanityEffect(final ThiefOfSanityEffect effect) {
        super(effect);
    }

    @Override
    public ThiefOfSanityEffect copy() {
        return new ThiefOfSanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && damagedPlayer != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAllCards(damagedPlayer.getLibrary().getTopCards(game, 3));
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile face down"));
            if (controller.choose(outcome, topCards, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    topCards.remove(card);
                    // move card to exile
                    UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    card.setFaceDown(true, game);
                    if (controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName() + " (" + controller.getName() + ")")) {
                        card.setFaceDown(true, game);
                        Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(ThiefOfSanity.VALUE_PREFIX + source.getSourceId().toString());
                        if (exileZones == null) {
                            exileZones = new HashSet<>();
                            game.getState().setValue(ThiefOfSanity.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                        }
                        exileZones.add(exileZoneId);
                        // rule information: https://blogs.magicjudges.org/rulestips/2018/11/thief-of-sanity-and-control-changing/
                        // allow to cast the card
                        ContinuousEffect effect = new ThiefOfSanityCastFromExileEffect(controller.getId());
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                        // and you may spend mana as though it were mana of any color to cast it
                        effect = new ThiefOfSanitySpendAnyManaEffect(controller.getId());
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                        // For as long as that card remains exiled, you may look at it
                        effect = new ThiefOfSanityLookEffect(controller.getId());
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                    }
                }
            }
            // put the rest into their graveyard
            controller.moveCards(topCards, Zone.GRAVEYARD, source, game);
            return true;
        }

        return false;
    }
}

class ThiefOfSanityCastFromExileEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public ThiefOfSanityCastFromExileEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "For as long as that card remains exiled, you may cast it";
    }

    private ThiefOfSanityCastFromExileEffect(final ThiefOfSanityCastFromExileEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThiefOfSanityCastFromExileEffect copy() {
        return new ThiefOfSanityCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null || theCard.isLand(game)) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards

        if (objectId.equals(cardId)
                && affectedControllerId.equals(authorizedPlayerId)) {
            Card card = game.getCard(objectId);
            // TODO: Allow to cast Zoetic Cavern face down
            return card != null;
        }
        return false;
    }
}

class ThiefOfSanitySpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final UUID authorizedPlayerId;

    public ThiefOfSanitySpendAnyManaEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "For as long as that card remains exiled, you may spend mana as though it were mana of any color to cast it";
    }

    private ThiefOfSanitySpendAnyManaEffect(final ThiefOfSanitySpendAnyManaEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThiefOfSanitySpendAnyManaEffect copy() {
        return new ThiefOfSanitySpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from exile to spell the zone change counter is increased by 1 (effect must applies before and on stack, use isCheckPlayableMode?)
            return affectedControllerId.equals(authorizedPlayerId);
        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarded
            this.discard();
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class ThiefOfSanityLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public ThiefOfSanityLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "For as long as that card remains exiled, you may look at it";
    }

    private ThiefOfSanityLookEffect(final ThiefOfSanityLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThiefOfSanityLookEffect copy() {
        return new ThiefOfSanityLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
        }
        return affectedControllerId.equals(authorizedPlayerId)
                && objectId.equals(cardId);
    }
}
