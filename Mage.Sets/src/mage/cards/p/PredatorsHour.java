package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
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
        if (controller == null) { return false; }

        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent == null) { return false; }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) { return false; }

        Card topCard = opponent.getLibrary().getFromTop(game);
        if (topCard == null) { return false; }

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
            // And you may spend mana as though it were mana of any color to cast it
            CardUtil.makeCardPlayable(game, source, topCard, Duration.Custom,
                CardUtil.SimpleCastManaAdjustment.AS_THOUGH_ANY_MANA_COLOR);

            // For as long as that card remains exiled, you may look at it
            ContinuousEffect effect = new PredatorsHourLookEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
            game.addEffect(effect, source);
        }

        return true;
    }
}

class PredatorsHourLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public PredatorsHourLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "You may look at the cards exiled with {this}";
    }

    private PredatorsHourLookEffect(final PredatorsHourLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) { return true; }

    @Override
    public PredatorsHourLookEffect copy() { return new PredatorsHourLookEffect(this); }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);

        // card is no longer in the origin zone, effect can be discarded
        if (cardId == null) { this.discard(); }

        return affectedControllerId.equals(authorizedPlayerId) && objectId.equals(cardId);
    }
}
