    
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class NeverendingTorment extends CardImpl {

    public NeverendingTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Search target player's library for X cards, where X is the number of cards in your hand, and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new NeverendingTormentEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());
    }

    private NeverendingTorment(final NeverendingTorment card) {
        super(card);
    }

    @Override
    public NeverendingTorment copy() {
        return new NeverendingTorment(this);
    }
}

class NeverendingTormentEffect extends OneShotEffect {

    public NeverendingTormentEffect() {
        super(Outcome.Benefit);
        staticText = "Search target player's library for X cards, where X is the number of cards in your hand, and exile them. Then that player shuffles";
    }

    private NeverendingTormentEffect(final NeverendingTormentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null
                && you != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(you.getHand().size(), new FilterCard());
            you.searchLibrary(target, source, game, targetPlayer.getId());
            for (UUID cardId : target.getTargets()) {
                final Card targetCard = game.getCard(cardId);
                if (targetCard != null) {
                    applied |= you.moveCardToExileWithInfo(targetCard, null, null, source, game, Zone.LIBRARY, true);
                }
            }
            targetPlayer.shuffleLibrary(source, game);
        }
        return applied;
    }

    @Override
    public NeverendingTormentEffect copy() {
        return new NeverendingTormentEffect(this);
    }
}
