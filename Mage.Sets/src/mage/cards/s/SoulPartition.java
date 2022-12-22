package mage.cards.s;

import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class SoulPartition extends CardImpl {

    public SoulPartition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may play it. A spell cast by an opponent this way costs {2} more to cast.
        this.getSpellAbility().addEffect(new SoulPartitionEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SoulPartition(final SoulPartition card) {
        super(card);
    }

    @Override
    public SoulPartition copy() {
        return new SoulPartition(this);
    }
}

// Custom effects taken from Elite Spellbinder.

class SoulPartitionEffect extends OneShotEffect {

    public SoulPartitionEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target nonland permanent. For as long as that card remains exiled, its owner may play it. A spell cast by an opponent this way costs {2} more to cast.";
    }

    private SoulPartitionEffect(final SoulPartitionEffect effect) {
        super(effect);
    }

    @Override
    public SoulPartitionEffect copy() {
        return new SoulPartitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(targetId);
        if (controller == null || permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        Card card = game.getCard(targetId);
        if (card != null && game.getState().getZone(targetId) == Zone.EXILED) {
            game.addEffect(new SoulPartitionCastEffect(card, game), source);
            if (controller.hasOpponent(card.getOwnerId(), game)) {
                game.addEffect(new SoulPartitionCostEffect(card, game), source);
            }
        }
        return true;
    }
}

class SoulPartitionCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    public SoulPartitionCastEffect(Card card, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
    }

    private SoulPartitionCastEffect(final SoulPartitionCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SoulPartitionCastEffect copy() {
        return new SoulPartitionCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = mor.getCard(game);
        if (card == null) {
            discard();
            return false;
        }
        return mor.refersTo(CardUtil.getMainCardId(game, sourceId), game)
                && card.isOwnedBy(affectedControllerId);
    }
}

class SoulPartitionCostEffect extends CostModificationEffectImpl {

    private final MageObjectReference mor;

    public SoulPartitionCostEffect(Card card, Game game) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.mor = new MageObjectReference(card, game, 1);
    }

    private SoulPartitionCostEffect(final SoulPartitionCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SoulPartitionCostEffect copy() {
        return new SoulPartitionCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (game.inCheckPlayableState()) { // during playable check, the card is still in exile zone, the zcc is one less
            UUID cardtoCheckId = CardUtil.getMainCardId(game, abilityToModify.getSourceId());
            return mor.getSourceId().equals(cardtoCheckId)
                    && mor.getZoneChangeCounter() == game.getState().getZoneChangeCounter(cardtoCheckId) + 1;
        } else {
            return mor.refersTo(CardUtil.getMainCardId(game, abilityToModify.getSourceId()), game);
        }
    }
}
