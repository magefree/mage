package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TormentOfHailfire extends CardImpl {
    
    public TormentOfHailfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Repeat the following process X times. Each opponent loses 3 life unless they sacrifice a nonland permanent or discards a card.
        this.getSpellAbility().addEffect(new TormentOfHailfireEffect());
        
    }
    
    private TormentOfHailfire(final TormentOfHailfire card) {
        super(card);
    }
    
    @Override
    public TormentOfHailfire copy() {
        return new TormentOfHailfire(this);
    }
}

class TormentOfHailfireEffect extends OneShotEffect {
    
    public TormentOfHailfireEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Repeat the following process X times. Each opponent loses 3 life unless that player sacrifices a nonland permanent or discards a card";
    }
    
    public TormentOfHailfireEffect(final TormentOfHailfireEffect effect) {
        super(effect);
    }
    
    @Override
    public TormentOfHailfireEffect copy() {
        return new TormentOfHailfireEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int repeat = source.getManaCostsToPay().getX();
            for (int i = 1; i <= repeat; i++) {
                
                for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null) {
                        int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, opponentId, game);
                        if (permanents > 0 && opponent.chooseUse(outcome, "Sacrifices a nonland permanent? (Iteration " + i + " of " + repeat + ")",
                                "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
                            Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                            target.setNotTarget(true);
                            if (opponent.choose(outcome, target, source, game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null) {
                                    if (permanent.sacrifice(source, game)) {
                                        continue;
                                    }
                                }
                            }
                        }
                        if (!opponent.getHand().isEmpty() && opponent.chooseUse(outcome, "Discard a card? (Iteration " + i + " of " + repeat + ")",
                                "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
                            opponent.discardOne(false, false, source, game);
                            continue;
                        }
                        opponent.loseLife(3, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
