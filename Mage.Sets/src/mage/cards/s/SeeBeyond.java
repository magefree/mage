
package mage.cards.s;

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
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SeeBeyond extends CardImpl {

    public SeeBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        this.getSpellAbility().addEffect(new SeeBeyondEffect());
    }

    private SeeBeyond(final SeeBeyond card) {
        super(card);
    }

    @Override
    public SeeBeyond copy() {
        return new SeeBeyond(this);
    }

}

class SeeBeyondEffect extends OneShotEffect {

    public SeeBeyondEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw two cards, then shuffle a card from your hand into your library";
    }

    public SeeBeyondEffect(SeeBeyondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            controller.drawCards(2, source, game);
            if (!controller.getHand().isEmpty()) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard("card to shuffle into your library"));
                controller.choose(Outcome.Detriment, controller.getHand(), target, source, game);
                Card card = controller.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.LIBRARY, source, game);
                    controller.shuffleLibrary(source, game);
                }
                return true;

            }
        }
        return true;
    }

    @Override
    public SeeBeyondEffect copy() {
        return new SeeBeyondEffect(this);
    }

}
