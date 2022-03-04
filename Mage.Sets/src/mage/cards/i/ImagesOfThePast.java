
package mage.cards.i;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Styxo
 */
public final class ImagesOfThePast extends CardImpl {

    public ImagesOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}");

        // Return up to two target creature cards from your graveyard to the battlefield, then exile those creatures.
        this.getSpellAbility().addEffect(new ImagesOfThePastEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));

    }

    private ImagesOfThePast(final ImagesOfThePast card) {
        super(card);
    }

    @Override
    public ImagesOfThePast copy() {
        return new ImagesOfThePast(this);
    }
}

class ImagesOfThePastEffect extends OneShotEffect {

    ImagesOfThePastEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return up to two target creature cards from your graveyard to the battlefield, then exile those creatures";
    }

    ImagesOfThePastEffect(final ImagesOfThePastEffect effect) {
        super(effect);
    }

    @Override
    public ImagesOfThePastEffect copy() {
        return new ImagesOfThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            List<UUID> targets = source.getTargets().get(0).getTargets();
            for (UUID targetId : targets) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    player.moveCards(card, Zone.BATTLEFIELD, source, game);
                    player.moveCards(card, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
