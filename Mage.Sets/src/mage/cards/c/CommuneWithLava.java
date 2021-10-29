package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CommuneWithLava extends CardImpl {

    public CommuneWithLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Exile the top X cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new CommuneWithLavaEffect());

    }

    private CommuneWithLava(final CommuneWithLava card) {
        super(card);
    }

    @Override
    public CommuneWithLava copy() {
        return new CommuneWithLava(this);
    }
}

class CommuneWithLavaEffect extends OneShotEffect {

    public CommuneWithLavaEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top X cards of your library. Until the end of your next turn, you may play those cards";
    }

    public CommuneWithLavaEffect(final CommuneWithLavaEffect effect) {
        super(effect);
    }

    @Override
    public CommuneWithLavaEffect copy() {
        return new CommuneWithLavaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            int amount = source.getManaCostsToPay().getX();
            Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
            controller.moveCardsToExile(cards, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceCard.getIdName());

            for (Card card : cards) {
                ContinuousEffect effect = new CommuneWithLavaMayPlayEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);
            }

            return true;
        }
        return false;
    }
}

class CommuneWithLavaMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    public CommuneWithLavaMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    public CommuneWithLavaMayPlayEffect(final CommuneWithLavaMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public CommuneWithLavaMayPlayEffect copy() {
        return new CommuneWithLavaMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
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
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(sourceId);
    }

}
