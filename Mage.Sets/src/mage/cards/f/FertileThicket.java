
package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FertileThicket extends CardImpl {

    public FertileThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Fertile Thicket enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Fertile Thicket enters the battlefield, you may look at the top five cards of your library. If you do, reveal up to one basic land card from among them, then put that card on top of your library and the rest on the bottom in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FertileThicketEffect(), true));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private FertileThicket(final FertileThicket card) {
        super(card);
    }

    @Override
    public FertileThicket copy() {
        return new FertileThicket(this);
    }
}

class FertileThicketEffect extends OneShotEffect {

    public FertileThicketEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may look at the top five cards of your library. If you do, reveal up to one basic land card from among them, then put that card on top of your library and the rest on the bottom in any order";
    }

    public FertileThicketEffect(final FertileThicketEffect effect) {
        super(effect);
    }

    @Override
    public FertileThicketEffect copy() {
        return new FertileThicketEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            controller.lookAtCards(sourceObject.getIdName(), cards, game);
            TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, StaticFilters.FILTER_CARD_BASIC_LAND);
            controller.chooseTarget(outcome, cards, target, source, game);
            Cards cardsRevealed = new CardsImpl(target.getTargets());
            if (!cardsRevealed.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), cardsRevealed, game);
                cards.removeAll(cardsRevealed);
                controller.putCardsOnTopOfLibrary(cardsRevealed, game, source, true);
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
