
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class TheMendingOfDominaria extends CardImpl {

    public TheMendingOfDominaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Put the top two cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new TheMendingOfDominariaFirstEffect());

        // III — Return all land cards from your graveyard to the battlefield, then shuffle your graveyard into your library.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheMendingOfDominariaSecondEffect());
        this.addAbility(sagaAbility);
    }

    private TheMendingOfDominaria(final TheMendingOfDominaria card) {
        super(card);
    }

    @Override
    public TheMendingOfDominaria copy() {
        return new TheMendingOfDominaria(this);
    }
}

class TheMendingOfDominariaFirstEffect extends OneShotEffect {

    public TheMendingOfDominariaFirstEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Mill two cards, then you may return a creature card from your graveyard to your hand";
    }

    public TheMendingOfDominariaFirstEffect(final TheMendingOfDominariaFirstEffect effect) {
        super(effect);
    }

    @Override
    public TheMendingOfDominariaFirstEffect copy() {
        return new TheMendingOfDominariaFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        new MillCardsControllerEffect(2).apply(game, source);
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

class TheMendingOfDominariaSecondEffect extends OneShotEffect {

    TheMendingOfDominariaSecondEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all land cards from your graveyard to the battlefield, then shuffle your graveyard into your library";
    }

    TheMendingOfDominariaSecondEffect(final TheMendingOfDominariaSecondEffect effect) {
        super(effect);
    }

    @Override
    public TheMendingOfDominariaSecondEffect copy() {
        return new TheMendingOfDominariaSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(
                    controller.getGraveyard().getCards(new FilterLandCard(), source.getControllerId(), source, game),
                    Zone.BATTLEFIELD, source, game, false, false, false, null
            );
            for (Card card : controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }
            controller.shuffleLibrary(source, game);
        }
        return false;
    }
}
