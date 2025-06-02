package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author kleese
 */
public final class ExpeditedInheritance extends CardImpl {

    public ExpeditedInheritance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");

        // Whenever a creature is dealt damage, its controller may exile that many cards from the top of their library. They may play those cards until the end of their next turn.
        this.addAbility(new DealtDamageAnyTriggeredAbility(new ExpeditedInheritanceExileEffect(),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, SetTargetPointer.PLAYER, false));
    }

    private ExpeditedInheritance(final ExpeditedInheritance card) {
        super(card);
    }

    @Override
    public ExpeditedInheritance copy() {
        return new ExpeditedInheritance(this);
    }
}

class ExpeditedInheritanceExileEffect extends OneShotEffect {

    ExpeditedInheritanceExileEffect() {
        super(Outcome.Benefit);
        staticText = "its controller may exile that many cards from the top of their library." +
                " They may play those cards until the end of their next turn.";
    }

    private ExpeditedInheritanceExileEffect(final ExpeditedInheritanceExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = SavedDamageValue.MANY.calculate(game, source, this);
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (amount <= 0 || player == null) {
            return false;
        }
        String message = amount > 1 ?
                "Exile " + CardUtil.numberToText(amount) + " cards from the top of your library. Until the end of your next turn, you may play those cards."
                : "Exile the top card of your library. Until the end of your next turn, you may play that card.";
        if (player.chooseUse(outcome, message, source, game)) {
            Set<Card> cards = player.getLibrary().getTopCards(game, amount);
            if (!cards.isEmpty()) {
                player.moveCards(cards, Zone.EXILED, source, game);
                for (Card card : cards) {
                    ContinuousEffect effect = new ExpeditedInheritanceMayPlayEffect(player.getId());
                    effect.setTargetPointer(new FixedTarget(card.getId(), game));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }

    @Override
    public ExpeditedInheritanceExileEffect copy() {
        return new ExpeditedInheritanceExileEffect(this);
    }
}

class ExpeditedInheritanceMayPlayEffect extends AsThoughEffectImpl {

    private final UUID cardOwnerId;
    private int triggeredOnTurn = 0;

    ExpeditedInheritanceMayPlayEffect(UUID playerId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play this card.";
        this.cardOwnerId = playerId;
    }

    private ExpeditedInheritanceMayPlayEffect(final ExpeditedInheritanceMayPlayEffect effect) {
        super(effect);
        triggeredOnTurn = effect.triggeredOnTurn;
        cardOwnerId = effect.cardOwnerId;
    }

    @Override
    public ExpeditedInheritanceMayPlayEffect copy() {
        return new ExpeditedInheritanceMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        triggeredOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return triggeredOnTurn != game.getTurnNum()
                && game.getPhase().getStep().getType() == PhaseStep.END_TURN
                && game.isActivePlayer(cardOwnerId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return cardOwnerId != null && cardOwnerId.equals(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectIdToCast);
    }
}
