
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class LostHours extends CardImpl {

    public LostHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target player reveals their hand. You choose a nonland card from it. That player puts that card into their library third from the top.
        this.getSpellAbility().addEffect(new LostHoursEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private LostHours(final LostHours card) {
        super(card);
    }

    @Override
    public LostHours copy() {
        return new LostHours(this);
    }
}

class LostHoursEffect extends OneShotEffect {

    public LostHoursEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand. You choose a nonland card from it. That player puts that card into their library third from the top";
    }

    public LostHoursEffect(final LostHoursEffect effect) {
        super(effect);
    }

    @Override
    public LostHoursEffect copy() {
        return new LostHoursEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null) {
            targetPlayer.revealCards(source, targetPlayer.getHand(), game);
            if (targetPlayer.getHand().size() > 0) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard(StaticFilters.FILTER_CARD_A_NON_LAND));
                if (controller.choose(Outcome.Discard, targetPlayer.getHand(), target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        targetPlayer.putCardOnTopXOfLibrary(card, game, source, 3, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
