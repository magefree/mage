package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PreferredSelection extends CardImpl {

    public PreferredSelection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // At the beginning of your upkeep, look at the top two cards of your library. You may sacrifice Preferred Selection and pay {2}{G}{G}. If you do, put one of those cards into your hand. If you don't, put one of those cards on the bottom of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new PreferredSelectionEffect(), TargetController.YOU, false
        ));
    }

    private PreferredSelection(final PreferredSelection card) {
        super(card);
    }

    @Override
    public PreferredSelection copy() {
        return new PreferredSelection(this);
    }
}

class PreferredSelectionEffect extends OneShotEffect {

    PreferredSelectionEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top two cards of your library. " +
                "You may sacrifice {this} and pay {2}{G}{G}. If you do, put one of those cards into your hand. " +
                "If you don't, put one of those cards on the bottom of your library.";
    }

    private PreferredSelectionEffect(final PreferredSelectionEffect effect) {
        super(effect);
    }

    @Override
    public PreferredSelectionEffect copy() {
        return new PreferredSelectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.lookAtCards(source, "Top two cards", cards, game);
        Cost cost = new CompositeCost(
                new SacrificeSourceCost(), new ManaCostsImpl<>("{2}{G}{G}"),
                "sacrifice this permanent and pay {2}{G}{G}"
        );
        return new DoIfCostPaid(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.TOP_ANY),
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.BOTTOM_ANY, PutCards.TOP_ANY),
                cost
        ).apply(game, source);
    }
}
