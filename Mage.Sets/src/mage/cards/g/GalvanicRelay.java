package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class GalvanicRelay extends CardImpl {

    public GalvanicRelay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top card of your library. During your next turn, you may play that card.
        this.getSpellAbility().addEffect(new GalvanicRelayEffect());

        // Storm
        this.addAbility(new StormAbility());
    }

    private GalvanicRelay(final GalvanicRelay card) {
        super(card);
    }

    @Override
    public GalvanicRelay copy() {
        return new GalvanicRelay(this);
    }
}

class GalvanicRelayEffect extends OneShotEffect {

    public GalvanicRelayEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of your library. During your next turn, you may play that card";
    }

    private GalvanicRelayEffect(final GalvanicRelayEffect effect) {
        super(effect);
    }

    @Override
    public GalvanicRelayEffect copy() {
        return new GalvanicRelayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card topCard = controller.getLibrary().getFromTop(game);
            if (topCard != null) {
                Card sourceCard = game.getCard(source.getSourceId());
                controller.moveCardsToExile(topCard, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceCard != null ? sourceCard.getIdName() : "");
                ContinuousEffect effect = new GalvanicRelayMayPlayEffect();
                effect.setTargetPointer(new FixedTarget(topCard.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

class GalvanicRelayMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    public GalvanicRelayMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "During your next turn, you may play that card";
    }

    private GalvanicRelayMayPlayEffect(final GalvanicRelayMayPlayEffect effect) {
        super(effect);
        this.castOnTurn = effect.castOnTurn;
    }

    @Override
    public GalvanicRelayMayPlayEffect copy() {
        return new GalvanicRelayMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        // Keep effect active until cleanup step of the controller's next turn. Turn will be checked again in applies method below.
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.CLEANUP) {
            return game.isActivePlayer(source.getControllerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID mainCardId = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && castOnTurn != game.getTurnNum()
                && game.isActivePlayer(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(mainCardId);
    }
}
