
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
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
 * @author LevelX2
 */
public final class CorpseChurn extends CardImpl {

    public CorpseChurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Put the top three cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        getSpellAbility().addEffect(new CorpseChurnEffect());
    }

    private CorpseChurn(final CorpseChurn card) {
        super(card);
    }

    @Override
    public CorpseChurn copy() {
        return new CorpseChurn(this);
    }
}

class CorpseChurnEffect extends OneShotEffect {

    public CorpseChurnEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then you may return a creature card from your graveyard to your hand";
    }

    public CorpseChurnEffect(final CorpseChurnEffect effect) {
        super(effect);
    }

    @Override
    public CorpseChurnEffect copy() {
        return new CorpseChurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (target.canChoose(source.getControllerId(), source, game)
                && controller.chooseUse(outcome, "Return a creature card from your graveyard to hand?", source, game)
                && controller.choose(Outcome.ReturnToHand, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
