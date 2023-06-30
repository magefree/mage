package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class AetherworksMarvel extends CardImpl {

    public AetherworksMarvel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.supertype.add(SuperType.LEGENDARY);

        // Whenever a permanent you control is put into a graveyard, you get {E}.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new GetEnergyCountersControllerEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_PERMANENT, false
        ));

        // {T}, Pay {E}{E}{E}{E}{E}{E}: Look at the top six cards of your library. 
        // You may cast a card from among them without paying its mana cost. 
        // Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new AetherworksMarvelEffect(), new TapSourceCost());
        ability.addCost(new PayEnergyCost(6));
        this.addAbility(ability);
    }

    private AetherworksMarvel(final AetherworksMarvel card) {
        super(card);
    }

    @Override
    public AetherworksMarvel copy() {
        return new AetherworksMarvel(this);
    }
}

class AetherworksMarvelEffect extends OneShotEffect {

    AetherworksMarvelEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Look at the top six cards of your library. "
                + "You may cast a card from among them without paying "
                + "its mana cost. Put the rest on the bottom of your "
                + "library in a random order";
    }

    AetherworksMarvelEffect(final AetherworksMarvelEffect effect) {
        super(effect);
    }

    @Override
    public AetherworksMarvelEffect copy() {
        return new AetherworksMarvelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 6));
        CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        cards.retainZone(Zone.LIBRARY, game);
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
