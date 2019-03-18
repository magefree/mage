
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class RitesOfSpring extends CardImpl {

    public RitesOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Discard any number of cards. Search your library for up to that many basic land cards, reveal those cards, and put them into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new RitesOfSpringEffect());
    }

    public RitesOfSpring(final RitesOfSpring card) {
        super(card);
    }

    @Override
    public RitesOfSpring copy() {
        return new RitesOfSpring(this);
    }
}

class RitesOfSpringEffect extends OneShotEffect {

    public RitesOfSpringEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard any number of cards. Search your library for up to that many basic land cards, reveal those cards, and put them into your hand. Then shuffle your library.";
    }

    public RitesOfSpringEffect(final RitesOfSpringEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfSpringEffect copy() {
        return new RitesOfSpringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard("cards to discard"));
            while (controller.canRespond() && !target.isChosen()) {
                target.choose(Outcome.BoostCreature, controller.getId(), source.getSourceId(), game);
            }
            int numDiscarded = 0;
            for (UUID targetId : target.getTargets()) {
                Card card = controller.getHand().get(targetId, game);
                if (controller.discard(card, source, game)) {
                    numDiscarded++;
                }
            }
            game.applyEffects();
            return new SearchLibraryPutInHandEffect(
                    new TargetCardInLibrary(0, numDiscarded, StaticFilters.FILTER_CARD_BASIC_LAND), true, true)
                    .apply(game, source);
        }
        return false;
    }
}
