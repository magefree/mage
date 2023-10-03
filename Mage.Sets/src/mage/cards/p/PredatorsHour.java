package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class PredatorsHour extends CardImpl {

    public PredatorsHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Until end of turn, creatures you control gain menace
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
                ).setText("Until end of turn, creatures you control gain menace")
        );

        // and “Whenever this creature deals combat damage to a player,
        // exile the top card of that player's library face down.
        // You may look at and play that card for as long as it remains exiled,
        // and you may spend mana as though it were mana of any color to cast that spell.”
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new PredatorsHourEffect(),
                        false,
                        true),
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
                ).setText("\"Whenever this creature deals combat damage to a player, " +
                        "exile the top card of that player's library face down. " +
                        "You may look at and play that card for as long as it remains exiled, " +
                        "and you may spend mana as though it were mana of any color to cast that spell.\"")
                .concatBy("and")
        );
    }

    private PredatorsHour(final PredatorsHour card) { super(card); }

    @Override
    public PredatorsHour copy() { return new PredatorsHour(this); }
}

// Based on Gonti, Lord of Luxury
class PredatorsHourEffect extends OneShotEffect {

    private static final String VALUE_PREFIX = "ExileZones";

    public PredatorsHourEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of that player's library face down. " +
                "You may look at and play that card for as long as it remains exiled, " +
                "and you may spend mana as though it were mana of any color to cast that spell.";
    }

    private PredatorsHourEffect(final PredatorsHourEffect effect) { super(effect); }

    @Override
    public PredatorsHourEffect copy() { return new PredatorsHourEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent == null) {
            return false;
        }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
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
            ContinuousEffect effect = new PredatorsHourPlayFromExileEffect();
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);

            // And you may spend mana as though it were mana of any color to cast it
            effect = new PredatorsHourSpendAnyManaEffect();
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

class PredatorsHourPlayFromExileEffect extends AsThoughEffectImpl {

    public PredatorsHourPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may look at and play that card for as long as it remains exiled, " +
                "and you may spend mana as though it were mana of any color to cast that spell.";
    }

    private PredatorsHourPlayFromExileEffect(final PredatorsHourPlayFromExileEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) { return true; }

    @Override
    public PredatorsHourPlayFromExileEffect copy() { return new PredatorsHourPlayFromExileEffect(this); }

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

class PredatorsHourSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public PredatorsHourSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast that spell";
    }

    private PredatorsHourSpendAnyManaEffect(final PredatorsHourSpendAnyManaEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) { return true; }

    @Override
    public PredatorsHourSpendAnyManaEffect copy() { return new PredatorsHourSpendAnyManaEffect(this); }

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