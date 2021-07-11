package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public final class StolenGoods extends CardImpl {

    public StolenGoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Target opponent exiles cards from the top of their library until they exile a nonland card. Until end of turn, you may cast that card without paying its mana cost.
        this.getSpellAbility().addEffect(new StolenGoodsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private StolenGoods(final StolenGoods card) {
        super(card);
    }

    @Override
    public StolenGoods copy() {
        return new StolenGoods(this);
    }
}

class StolenGoodsEffect extends OneShotEffect {

    public StolenGoodsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent exiles cards from the top of their library until they exile a nonland card. Until end of turn, you may cast that card without paying its mana cost";
    }

    public StolenGoodsEffect(final StolenGoodsEffect effect) {
        super(effect);
    }

    @Override
    public StolenGoodsEffect copy() {
        return new StolenGoodsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent != null) {
            Card card;
            do {
                card = opponent.getLibrary().getFromTop(game);
                if (card != null) {
                    opponent.moveCardsToExile(card, source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, null));
                }
            } while (card != null && card.isLand(game));

            if (card != null) {
                ContinuousEffect effect = new StolenGoodsCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class StolenGoodsCastFromExileEffect extends AsThoughEffectImpl {

    public StolenGoodsCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast card from exile";
    }

    public StolenGoodsCastFromExileEffect(final StolenGoodsCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StolenGoodsCastFromExileEffect copy() {
        return new StolenGoodsCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId != null && objectId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
            return true;
        }
        return false;
    }
}
