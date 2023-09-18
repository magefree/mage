package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapeToTheWilds extends CardImpl {

    public EscapeToTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{G}");

        // Exile the top five cards of your library. You may play cards exiled this way until the end of your next turn.
        // You may play an additional land this turn.
        this.getSpellAbility().addEffect(new EscapeToTheWildsEffect());
    }

    private EscapeToTheWilds(final EscapeToTheWilds card) {
        super(card);
    }

    @Override
    public EscapeToTheWilds copy() {
        return new EscapeToTheWilds(this);
    }
}

class EscapeToTheWildsEffect extends OneShotEffect {

    EscapeToTheWildsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top five cards of your library. "
                + "You may play cards exiled this way until the end of your next turn.<br>"
                + "You may play an additional land this turn.";
    }

    private EscapeToTheWildsEffect(final EscapeToTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public EscapeToTheWildsEffect copy() {
        return new EscapeToTheWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        Card sourceCard = game.getCard(source.getSourceId());
        controller.moveCards(cards, Zone.EXILED, source, game);

        cards.getCards(game).stream().forEach(card -> {
            ContinuousEffect effect = new EscapeToTheWildsMayPlayEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
        });
        game.addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn), source);
        return true;
    }
}

class EscapeToTheWildsMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    EscapeToTheWildsMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    private EscapeToTheWildsMayPlayEffect(final EscapeToTheWildsMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public EscapeToTheWildsMayPlayEffect copy() {
        return new EscapeToTheWildsMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return castOnTurn != game.getTurnNum()
                && game.getPhase().getStep().getType() == PhaseStep.END_TURN
                && game.isActivePlayer(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectIdToCast);
    }
}
