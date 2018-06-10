package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Derpthemeus
 */
public final class RootingKavu extends CardImpl {

    public RootingKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Rooting Kavu dies, you may exile it. If you do, shuffle all creature cards from your graveyard into your library.
        this.addAbility(new DiesTriggeredAbility(new DoIfCostPaid(new RootingKavuEffect(), new ExileSourceFromGraveCost())));
    }

    public RootingKavu(final RootingKavu card) {
        super(card);
    }

    @Override
    public RootingKavu copy() {
        return new RootingKavu(this);
    }

}

class RootingKavuEffect extends OneShotEffect {

    public RootingKavuEffect() {
        super(Outcome.Benefit);
        this.staticText = "shuffle all creature cards from your graveyard into your library.";
    }

    public RootingKavuEffect(final RootingKavuEffect effect) {
        super(effect);
    }

    @Override
    public RootingKavuEffect copy() {
        return new RootingKavuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
            controller.putCardsOnTopOfLibrary(cards, game, source, false);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
