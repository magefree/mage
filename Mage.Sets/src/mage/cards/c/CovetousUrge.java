package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetousUrge extends CardImpl {

    public CovetousUrge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U/B}{U/B}{U/B}{U/B}");

        // Target opponent reveals their hand. You choose a nonland card from that player's graveyard or hand and exile it. You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addEffect(new CovetousUrgeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CovetousUrge(final CovetousUrge card) {
        super(card);
    }

    @Override
    public CovetousUrge copy() {
        return new CovetousUrge(this);
    }
}

class CovetousUrgeEffect extends OneShotEffect {

    CovetousUrgeEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals their hand. You choose a nonland card from that player's graveyard " +
                "or hand and exile it. You may cast that card for as long as it remains exiled," +
                " and you may spend mana as though it were mana of any color to cast that spell.";
    }

    private CovetousUrgeEffect(final CovetousUrgeEffect effect) {
        super(effect);
    }

    @Override
    public CovetousUrgeEffect copy() {
        return new CovetousUrgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        if (!controller.chooseUse(outcome, "Choose a nonland card to exile?", source, game)) {
            return false;
        }
        TargetCard target;
        if (player.getGraveyard().isEmpty() || controller.chooseUse(outcome,
                "Exile a nonland card from " + player.getName() + "'s hand or graveyard",
                "", "Hand", "Graveyard", source, game)) {
            if (player.getHand().isEmpty()) {
                return true;
            }
            target = new TargetCardInHand(StaticFilters.FILTER_CARD_A_NON_LAND);
            controller.choose(outcome, player.getHand(), target, game);
        } else {
            target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_A_NON_LAND);
            controller.choose(outcome, player.getGraveyard(), target, game);
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !controller.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }
        if (card.getSpellAbility() == null) {
            return true;
        }
        game.addEffect(new CovetousUrgeCastFromExileEffect(new MageObjectReference(card, game)), source);
        game.addEffect(new CovetousUrgeSpendAnyManaEffect().setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class CovetousUrgeCastFromExileEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    CovetousUrgeCastFromExileEffect(MageObjectReference mor) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.mor = mor;
    }

    private CovetousUrgeCastFromExileEffect(final CovetousUrgeCastFromExileEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CovetousUrgeCastFromExileEffect copy() {
        return new CovetousUrgeCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (mor.getCard(game) == null) {
            discard();
            return false;
        }
        return mor.refersTo(sourceId, game) && source.isControlledBy(affectedControllerId);
    }
}

class CovetousUrgeSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    CovetousUrgeSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
    }

    private CovetousUrgeSpendAnyManaEffect(final CovetousUrgeSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CovetousUrgeSpendAnyManaEffect copy() {
        return new CovetousUrgeSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, fixedTarget.getTarget())
                && fixedTarget.getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
