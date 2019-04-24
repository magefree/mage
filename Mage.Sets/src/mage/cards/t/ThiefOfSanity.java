package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
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

        Ability ability = new SimpleStaticAbility(Zone.ALL, new ThiefOfSanityLookEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability);
    }

    public ThiefOfSanity(final ThiefOfSanity card) {
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
        this.staticText = "look at the top three cards of that player's library, exile one of them face down, then put the rest into their graveyard. For as long as that card remains exiled, you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it";
    }

    public ThiefOfSanityEffect(final ThiefOfSanityEffect effect) {
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
            topCards.addAll(damagedPlayer.getLibrary().getTopCards(game, 3));
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile face down"));
            if (controller.choose(outcome, topCards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    topCards.remove(card);
                    // move card to exile
                    UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    card.setFaceDown(true, game);
                    if (controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName())) {
                        card.setFaceDown(true, game);
                        Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(ThiefOfSanity.VALUE_PREFIX + source.getSourceId().toString());
                        if (exileZones == null) {
                            exileZones = new HashSet<>();
                            game.getState().setValue(ThiefOfSanity.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                        }
                        exileZones.add(exileZoneId);
                        // allow to cast the card
                        ContinuousEffect effect = new ThiefOfSanityCastFromExileEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), game));
                        game.addEffect(effect, source);
                        // and you may spend mana as though it were mana of any color to cast it
                        effect = new ThiefOfSanitySpendAnyManaEffect();
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

    public ThiefOfSanityCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    public ThiefOfSanityCastFromExileEffect(final ThiefOfSanityCastFromExileEffect effect) {
        super(effect);
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
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            this.discard();
        } else if (objectId.equals(targetId)
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            // TODO: Allow to cast Zoetic Cavern face down
            return card != null && !card.isLand();
        }
        return false;
    }
}

class ThiefOfSanitySpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public ThiefOfSanitySpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public ThiefOfSanitySpendAnyManaEffect(final ThiefOfSanitySpendAnyManaEffect effect) {
        super(effect);
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
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {

            if (affectedControllerId.equals(source.getControllerId())) {
                // if the card moved from exile to spell the zone change counter is increased by 1
                if (game.getState().getZoneChangeCounter(objectId) == ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
                    return true;
                }
            }

        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarted
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

    public ThiefOfSanityLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at the cards exiled with {this}";
    }

    public ThiefOfSanityLookEffect(final ThiefOfSanityLookEffect effect) {
        super(effect);
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
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null && card.isFaceDown(game)) {
                    Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(ThiefOfSanity.VALUE_PREFIX + source.getSourceId().toString());
                    if (exileZones != null) {
                        for (ExileZone exileZone : game.getExile().getExileZones()) {
                            if (exileZone.contains(objectId)) {
                                if (!exileZones.contains(exileZone.getId())) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
