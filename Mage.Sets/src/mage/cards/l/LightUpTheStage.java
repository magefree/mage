package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpectacleAbility;
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
 * @author TheElk801 and jeffwadsworth
 */
public final class LightUpTheStage extends CardImpl {

    public LightUpTheStage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new LightUpTheStageEffect());

        // Spectacle {R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl("{R}")));
    }

    private LightUpTheStage(final LightUpTheStage card) {
        super(card);
    }

    @Override
    public LightUpTheStage copy() {
        return new LightUpTheStage(this);
    }
}

class LightUpTheStageEffect extends OneShotEffect {

    LightUpTheStageEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top two cards of your library. Until the end of your next turn, you may play those cards";
    }

    private LightUpTheStageEffect(final LightUpTheStageEffect effect) {
        super(effect);
    }

    @Override
    public LightUpTheStageEffect copy() {
        return new LightUpTheStageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cards = controller.getLibrary().getTopCards(game, 2);
            Card sourceCard = game.getCard(source.getSourceId());
            controller.moveCardsToExile(cards, source, game, true, CardUtil.getCardExileZoneId(game, source), sourceCard != null ? sourceCard.getIdName() : "");

            for (Card card : cards) {
                ContinuousEffect effect = new LightUpTheStageMayPlayEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }

            return true;
        }
        return false;
    }
}

class LightUpTheStageMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    LightUpTheStageMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    private LightUpTheStageMayPlayEffect(final LightUpTheStageMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public LightUpTheStageMayPlayEffect copy() {
        return new LightUpTheStageMayPlayEffect(this);
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
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectIdToCast);
    }
}
