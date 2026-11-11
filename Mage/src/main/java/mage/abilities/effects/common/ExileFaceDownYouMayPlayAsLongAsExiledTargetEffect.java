package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CastManaAdjustment;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This exiles the target card or cards.
 * Each can be looked at by the source's controller.
 * For each card exiled this way, that player may play|cast that card as long as it stays exiled. (+ mana adjustement)
 * e.g. [[Gonti, Lord of Luxury]]
 *
 * @author Susucr
 */
public class ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect extends OneShotEffect {

    private final boolean useCastSpellOnly;
    private final CastManaAdjustment manaAdjustment;

    public ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(boolean useCastSpellOnly, CastManaAdjustment manaAdjustment) {
        super(Outcome.Exile);
        switch (manaAdjustment) {
            case NONE:
            case AS_THOUGH_ANY_MANA_TYPE:
            case AS_THOUGH_ANY_MANA_COLOR:
            case WITHOUT_PAYING_MANA_COST:
                this.manaAdjustment = manaAdjustment;
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage, manaAdjustment is not yet supported: " + manaAdjustment);
        }
        this.useCastSpellOnly = useCastSpellOnly;
    }

    private ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(final ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect effect) {
        super(effect);
        this.manaAdjustment = effect.manaAdjustment;
        this.useCastSpellOnly = effect.useCastSpellOnly;
    }

    @Override
    public ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect copy() {
        return new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        );
        if (controller == null || cards.isEmpty()) {
            return false;
        }
        // move card to exile
        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        MageObject sourceObject = source.getSourceObject(game);
        String exileName = sourceObject == null ? "" : sourceObject.getIdName();
        for (Card card : cards.getCards(game)) {
            card.setFaceDown(true, game);
            if (controller.moveCardsToExile(card, source, game, false, exileZoneId, exileName)) {
                card.setFaceDown(true, game);
                switch (manaAdjustment) {
                    case NONE:
                        CardUtil.makeCardPlayable(game, source, card, useCastSpellOnly, Duration.Custom, false, controller.getId(), null);
                        break;
                    case AS_THOUGH_ANY_MANA_TYPE:
                    case AS_THOUGH_ANY_MANA_COLOR:
                        // TODO: untangle why there is a confusion between the two.
                        CardUtil.makeCardPlayable(game, source, card, useCastSpellOnly, Duration.Custom, true, controller.getId(), null);
                        break;
                    case WITHOUT_PAYING_MANA_COST:
                        CardUtil.makeCardPlayable(game, source, card, useCastSpellOnly, Duration.Custom, false, controller.getId(), null);
                        game.addEffect(new CastWithoutPayingManaCostEffect(card.getId(), controller.getId()), source);
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong code usage, manaAdjustment is not yet supported: " + manaAdjustment);
                }
                // For as long as that card remains exiled, you may look at it
                ContinuousEffect effect = new MayLookAtTargetCardEffect(controller.getId());
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }

    private static class CastWithoutPayingManaCostEffect extends ContinuousEffectImpl {

        private final UUID cardId;
        private final UUID playerId;
        private final AlternativeCostSourceAbility alternativeCost;

        CastWithoutPayingManaCostEffect(UUID cardId, UUID playerId) {
            super(Duration.Custom, Outcome.PlayForFree);
            this.cardId = cardId;
            this.playerId = playerId;

            FilterCard filter = new FilterCard("this card");
            filter.add(new CardIdPredicate(cardId));
            this.alternativeCost = new AlternativeCostSourceAbility(
                    null, SourceIsSpellCondition.instance, null, filter, true
            );
        }

        private CastWithoutPayingManaCostEffect(final CastWithoutPayingManaCostEffect effect) {
            super(effect);
            this.cardId = effect.cardId;
            this.playerId = effect.playerId;
            this.alternativeCost = effect.alternativeCost.copy();
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            Card card = game.getCard(cardId);
            Zone zone = game.getState().getZone(cardId);
            if (card == null || (zone != Zone.EXILED && zone != Zone.STACK)) {
                discard();
                return false;
            }
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return false;
            }
            alternativeCost.setSourceId(source.getSourceId());
            player.getAlternativeSourceCosts().add(alternativeCost);
            return true;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.RulesEffects;
        }

        @Override
        public CastWithoutPayingManaCostEffect copy() {
            return new CastWithoutPayingManaCostEffect(this);
        }
    }
}
