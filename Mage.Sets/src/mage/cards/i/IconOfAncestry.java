package mage.cards.i;

import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author TheElk801
 */
public final class IconOfAncestry extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.instance);
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public IconOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Icon of Ancestry enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)
        ));

        // {3}, {T}: Look at the top three cards of your library. You may reveal a creature card of the 
        // chosen type from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new IconOfAncestryEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private IconOfAncestry(final IconOfAncestry card) {
        super(card);
    }

    @Override
    public IconOfAncestry copy() {
        return new IconOfAncestry(this);
    }
}

class IconOfAncestryEffect extends OneShotEffect {

    public IconOfAncestryEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Look at the top three cards of your library. "
                + "You may reveal a creature card of the "
                + "chosen type from among them and put it into your hand. "
                + "Put the rest on the bottom of your library in a random order";
    }

    public IconOfAncestryEffect(final IconOfAncestryEffect effect) {
        super(effect);
    }

    @Override
    public IconOfAncestryEffect copy() {
        return new IconOfAncestryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || !controller.getLibrary().hasCards()) {
            return false;
        }
        Set<Card> cardsFromTopOfLibrary = controller.getLibrary().getTopCards(game, 3);
        Cards cardsFromLibrary = new CardsImpl();
        Cards revealedCard = new CardsImpl();
        cardsFromLibrary.addAll(cardsFromTopOfLibrary);
        if (cardsFromTopOfLibrary.isEmpty()) {
            return false;
        }
        FilterCreatureCard filter = new FilterCreatureCard("creature card that matches the chosen subtype");
        SubType subtype = (SubType) game.getState().getValue(source.getSourceId() + "_type");
        if (subtype != null) {
            filter.add(subtype.getPredicate());
        }
        TargetCard target = new TargetCard(Zone.LIBRARY, filter);
        if (target.canChoose(controller.getId(), game)
                && controller.chooseUse(outcome, "Do you wish to use Icon of Ancestry's effect?", source, game)
                && controller.choose(Outcome.Benefit, cardsFromLibrary, target, game)) {
            Card chosenCard = game.getCard(target.getFirstTarget());
            if (chosenCard != null) {
                revealedCard.add(chosenCard);
                controller.revealCards(source, revealedCard, game);
                controller.putInHand(chosenCard, game);
                cardsFromLibrary.remove(chosenCard);
            }
        }
        controller.putCardsOnBottomOfLibrary(cardsFromLibrary, game, source, true);
        return true;
    }
}
