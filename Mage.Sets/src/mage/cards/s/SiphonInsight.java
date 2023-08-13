package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801 plus everyone who worked on Gonti
 */
public final class SiphonInsight extends CardImpl {

    public SiphonInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Look at the top two cards of target opponent's library. Exile one of them face down and put the other on the bottom of that library. You may look at and play the exiled card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addEffect(new SiphonInsightEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Flashback {1}{U}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}{B}")));
    }

    private SiphonInsight(final SiphonInsight card) {
        super(card);
    }

    @Override
    public SiphonInsight copy() {
        return new SiphonInsight(this);
    }
}

class SiphonInsightEffect extends OneShotEffect {

    private static final String VALUE_PREFIX = "ExileZones";

    public SiphonInsightEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top two cards of target opponent's library. " +
                "Exile one of them face down and put the other on the bottom of that library. " +
                "You may look at and play the exiled card for as long as it remains exiled, " +
                "and you may spend mana as though it were mana of any color to cast that spell";
    }

    private SiphonInsightEffect(final SiphonInsightEffect effect) {
        super(effect);
    }

    @Override
    public SiphonInsightEffect copy() {
        return new SiphonInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || opponent == null || sourceObject == null) {
            return false;
        }
        Cards topCards = new CardsImpl();
        topCards.addAllCards(opponent.getLibrary().getTopCards(game, 2));
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile"));
        controller.choose(outcome, topCards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
            return true;
        }
        topCards.remove(card);
        // move card to exile
        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        card.setFaceDown(true, game);
        if (controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName())) {
            card.setFaceDown(true, game);
            Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(VALUE_PREFIX + source.getSourceId().toString());
            if (exileZones == null) {
                exileZones = new HashSet<>();
                game.getState().setValue(VALUE_PREFIX + source.getSourceId().toString(), exileZones);
            }
            exileZones.add(exileZoneId);
            // allow to cast the card
            ContinuousEffect effect = new SiphonInsightCastFromExileEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
            // and you may spend mana as though it were mana of any color to cast it
            effect = new SiphonInsightSpendAnyManaEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
            // For as long as that card remains exiled, you may look at it
            effect = new SiphonInsightLookEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
        }
        // then put the rest on the bottom of that library in a random order
        controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
        return true;
    }
}

class SiphonInsightCastFromExileEffect extends AsThoughEffectImpl {

    public SiphonInsightCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    private SiphonInsightCastFromExileEffect(final SiphonInsightCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SiphonInsightCastFromExileEffect copy() {
        return new SiphonInsightCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards

        if (objectId.equals(targetId)
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            // TODO: Allow to cast Zoetic Cavern face down
            return card != null;
        }
        return false;
    }
}

class SiphonInsightSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public SiphonInsightSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    private SiphonInsightSpendAnyManaEffect(final SiphonInsightSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SiphonInsightSpendAnyManaEffect copy() {
        return new SiphonInsightSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        objectId = theCard.getMainCard().getId(); // for split cards
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

class SiphonInsightLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public SiphonInsightLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "You may look at the cards exiled with {this}";
    }

    private SiphonInsightLookEffect(final SiphonInsightLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SiphonInsightLookEffect copy() {
        return new SiphonInsightLookEffect(this);
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
