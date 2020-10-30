package mage.cards.a;

import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class AetherworksMarvel extends CardImpl {

    public AetherworksMarvel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        addSuperType(SuperType.LEGENDARY);

        // Whenever a permanent you control is put into a graveyard, you get {E}.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new GetEnergyCountersControllerEffect(1), false,
                new FilterControlledPermanent("a permanent you control"), false));

        // {T}, Pay {E}{E}{E}{E}{E}{E}: Look at the top six cards of your library. 
        // You may cast a card from among them without paying its mana cost. 
        // Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new AetherworksMarvelEffect(), new TapSourceCost());
        ability.addCost(new PayEnergyCost(6));
        this.addAbility(ability);
    }

    public AetherworksMarvel(final AetherworksMarvel card) {
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
        if (controller != null) {
            Set<Card> cardsSet = controller.getLibrary().getTopCards(game, 6);
            Cards cards = new CardsImpl(cardsSet);
            TargetCard target = new TargetCardInLibrary(0, 1, 
                    new FilterNonlandCard("card to cast without paying its mana cost"));
            if (controller.choose(Outcome.PlayForFree, cards, target, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    if (cardWasCast) {
                        cards.remove(card);
                    }
                }
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        return false;
    }
}
