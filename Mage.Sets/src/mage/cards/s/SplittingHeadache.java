
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SplittingHeadache extends CardImpl {

    public SplittingHeadache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");


        // Choose one - Target player discards two cards; or target player reveals their hand, you choose a card from it, then that player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        Mode mode = new Mode();
        mode.addEffect(new SplittingHeadacheEffect());
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

    }

    public SplittingHeadache(final SplittingHeadache card) {
        super(card);
    }

    @Override
    public SplittingHeadache copy() {
        return new SplittingHeadache(this);
    }
}

class SplittingHeadacheEffect extends OneShotEffect {

    public SplittingHeadacheEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand, you choose a card from it, then that player discards that card.";
    }

    public SplittingHeadacheEffect(final SplittingHeadacheEffect effect) {
        super(effect);
    }

    @Override
    public SplittingHeadacheEffect copy() {
        return new SplittingHeadacheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.revealCards("Splitting Headache", player.getHand(), game);
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
                if (you.choose(Outcome.Benefit, player.getHand(), target, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    return player.discard(card, source, game);

                }
            }
        }
        return false;
    }
}
