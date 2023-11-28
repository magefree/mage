package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.*;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.Ability;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author Xanderhall
 */
public final class BlightwingBandit extends CardImpl {

    public BlightwingBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        //  Whenever you cast your first spell during each opponent's turn, look at the top card of that player's library, then exile it face down. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(new BlightwingBanditEffect(), false, SetTargetPointer.PLAYER));
    }

    private BlightwingBandit(final BlightwingBandit card) {
        super(card);
    }

    @Override
    public BlightwingBandit copy() {
        return new BlightwingBandit(this);
    }
}

class BlightwingBanditEffect extends OneShotEffect {

    private static final String VALUE_PREFIX = "ExileZones";

    public BlightwingBanditEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of that player's library, then exile it face down. "
        + "You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it.";
    }

    private BlightwingBanditEffect(final BlightwingBanditEffect effect) { 
        super(effect); 
    }

    @Override
    public BlightwingBanditEffect copy() { 
        return new BlightwingBanditEffect(this); 
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);

        if (controller == null || opponent == null || sourceObject == null) { 
            return false; 
        }

        Card topCard = opponent.getLibrary().getFromTop(game);
        if (topCard == null) { 
            return false; 
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        topCard.setFaceDown(true, game);

        // Move card to exile
        if (controller.moveCardsToExile(topCard, source, game, false, exileZoneId, sourceObject.getIdName())) {
            topCard.setFaceDown(true, game);

            Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(VALUE_PREFIX + source.getSourceId().toString());
            if (exileZones == null) {
                exileZones = new HashSet<>();
                game.getState().setValue(VALUE_PREFIX + source.getSourceId().toString(), exileZones);
            }
            exileZones.add(exileZoneId);

            // You may play the card
            ContinuousEffect effect = new BlightwingBanditPlayFromExileEffect();
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);

            // And you may spend mana as though it were mana of any color to cast it
            effect = new BlightwingBanditSpendAnyManaEffect();
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);

            // For as long as that card remains exiled, you may look at it
            effect = new MayLookAtTargetCardEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);
        }

        return true;
    }
}

class BlightwingBanditPlayFromExileEffect extends AsThoughEffectImpl {

    public BlightwingBanditPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may look at and play that card for as long as it remains exiled, " +
                "and you may spend mana as though it were mana of any color to cast that spell.";
    }

    private BlightwingBanditPlayFromExileEffect(final BlightwingBanditPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true; 
    }

    @Override
    public BlightwingBanditPlayFromExileEffect copy() { 
        return new BlightwingBanditPlayFromExileEffect(this); 
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            // card is no longer in the origin zone, effect can be discarded
            this.discard();
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null ) { 
            return false; 
        }

        // for split cards
        objectId = theCard.getMainCard().getId();

        if (objectId.equals(targetId)  && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            return card != null;
        }
        return false;
    }
}

class BlightwingBanditSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public BlightwingBanditSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast that spell";
    }

    private BlightwingBanditSpendAnyManaEffect(final BlightwingBanditSpendAnyManaEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) { 
        return true; 
    }

    @Override
    public BlightwingBanditSpendAnyManaEffect copy() { 
        return new BlightwingBanditSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if (theCard == null) { 
            return false; 
        }

        // for split cards
        objectId = theCard.getMainCard().getId();

        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from exile to spell the zone change counter is increased by 1 (effect must applies before and on stack, use isCheckPlayableMode?)
            return source.isControlledBy(affectedControllerId);
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
