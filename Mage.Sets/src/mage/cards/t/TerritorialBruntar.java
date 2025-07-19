package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TerritorialBruntar extends CardImpl {

    public TerritorialBruntar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Landfall — Whenever a land you control enters, exile cards from the top of your library until you exile a nonland card. You may cast that card this turn.
        this.addAbility(new LandfallAbility(new TerritorialBruntarEffect()));
    }

    private TerritorialBruntar(final TerritorialBruntar card) {
        super(card);
    }

    @Override
    public TerritorialBruntar copy() {
        return new TerritorialBruntar(this);
    }
}

class TerritorialBruntarEffect extends OneShotEffect {

    TerritorialBruntarEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. You may cast that card this turn";
    }

    private TerritorialBruntarEffect(final TerritorialBruntarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean didSomething = false;
        for (Card card : controller.getLibrary().getCards(game)) {
            didSomething |= controller.moveCards(card, Zone.EXILED, source, game);
            if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand(game)) {
                ContinuousEffect effect = new TerritorialBruntarAsThoughEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return didSomething;
    }

    @Override
    public TerritorialBruntarEffect copy() {
        return new TerritorialBruntarEffect(this);
    }
}

class TerritorialBruntarAsThoughEffect extends AsThoughEffectImpl {

    TerritorialBruntarAsThoughEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may cast that card this turn";
    }

    private TerritorialBruntarAsThoughEffect(final TerritorialBruntarAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TerritorialBruntarAsThoughEffect copy() {
        return new TerritorialBruntarAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            // cleanup if the target is no longer in the exile
            discard();
            return false;
        }
        return targetId.equals(objectId)
                && source.isControlledBy(affectedControllerId)
                && Zone.EXILED == game.getState().getZone(objectId);
    }
}
