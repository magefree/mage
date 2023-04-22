package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class MindWarp extends CardImpl {

    public MindWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{3}{B}");

        // Look at target player's hand and choose X cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new MindWarpEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MindWarp(final MindWarp card) {
        super(card);
    }

    @Override
    public MindWarp copy() {
        return new MindWarp(this);
    }
}

class MindWarpEffect extends OneShotEffect {

    MindWarpEffect() {
        super(Outcome.Discard);
        staticText = "Look at target player's hand and choose X cards from it. That player discards those cards.";
    }

    private MindWarpEffect(final MindWarpEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || you == null) {
            return false;
        }
        int amountToDiscard = source.getManaCostsToPay().getX();
        TargetCard target = new TargetCardInHand(amountToDiscard, StaticFilters.FILTER_CARD_CARDS);
        you.choose(outcome, targetPlayer.getHand(), target, source, game);
        targetPlayer.discard(new CardsImpl(target.getTargets()), false, source, game);
        return true;
    }

    @Override
    public MindWarpEffect copy() {
        return new MindWarpEffect(this);
    }
}
