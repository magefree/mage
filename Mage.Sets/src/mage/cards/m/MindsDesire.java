
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class MindsDesire extends CardImpl {

    public MindsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.
        this.getSpellAbility().addEffect(new MindsDesireEffect());

        // Storm
        this.addAbility(new StormAbility());
    }

    public MindsDesire(final MindsDesire card) {
        super(card);
    }

    @Override
    public MindsDesire copy() {
        return new MindsDesire(this);
    }
}

class MindsDesireEffect extends OneShotEffect {

    MindsDesireEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost";
    }

    MindsDesireEffect(final MindsDesireEffect effect) {
        super(effect);
    }

    @Override
    public MindsDesireEffect copy() {
        return new MindsDesireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.shuffleLibrary(source, game);
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileId = UUID.randomUUID();
                controller.moveCardsToExile(card, source, game, true, exileId, CardUtil.createObjectRealtedWindowTitle(source, game, null));
                ContinuousEffect effect = new MindsDesireCastFromExileEffect();
                effect.setTargetPointer(new FixedTargets(game.getExile().getExileZone(exileId).getCards(game), game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class MindsDesireCastFromExileEffect extends AsThoughEffectImpl {

    MindsDesireCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may play that card without paying its mana cost";
    }

    MindsDesireCastFromExileEffect(final MindsDesireCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MindsDesireCastFromExileEffect copy() {
        return new MindsDesireCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && getTargetPointer().getTargets(game, source).contains(objectId)) {
            Card card = game.getCard(objectId);
            if (card != null && !card.isLand() && card.getSpellAbility().getCosts() != null) {
                Player player = game.getPlayer(affectedControllerId);
                if (player != null) {
                    player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                }
            }
            return true;
        }
        return false;
    }
}
