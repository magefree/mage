
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Quercitron
 */
public final class Recall extends CardImpl {

    public Recall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{X}{U}");

        // Discard X cards, then return a card from your graveyard to your hand for each card discarded this way.
        this.getSpellAbility().addEffect(new RecallEffect());
        // Exile Recall.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private Recall(final Recall card) {
        super(card);
    }

    @Override
    public Recall copy() {
        return new Recall(this);
    }
}

class RecallEffect extends OneShotEffect {

    public RecallEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Discard X cards, then return a card from your graveyard to your hand for each card discarded this way";
    }

    public RecallEffect(final RecallEffect effect) {
        super(effect);
    }

    @Override
    public RecallEffect copy() {
        return new RecallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Discard X cards
            Cards cardsDiscarded = controller.discard(source.getManaCostsToPay().getX(), false, false, source, game);
            if (!cardsDiscarded.isEmpty()) {
                // then return a card from your graveyard to your hand for each card discarded this way
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(cardsDiscarded.size(), new FilterCard());
                target.setNotTarget(true);
                target.choose(Outcome.ReturnToHand, controller.getId(), source.getSourceId(), source, game);
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            }

            return true;
        }
        return false;
    }

}
