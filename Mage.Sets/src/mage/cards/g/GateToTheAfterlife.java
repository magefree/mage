
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author LevelX2
 */
public final class GateToTheAfterlife extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public GateToTheAfterlife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a nontoken creature you control dies, you gain 1 life. Then you may draw a card. If you do, discard a card.
        Ability ability = new DiesCreatureTriggeredAbility(new GainLifeEffect(1), false, filter, false);
        Effect effect = new DrawDiscardControllerEffect(1, 1, true);
        effect.setText("Then you may draw a card. If you do, discard a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Gate to the Afterlife: Search your graveyard, hand, and/or library for a card named God-Pharaoh's Gift and put it onto the battlefield. If you seearch your library this way, shuffle it. Activate this ability only if there are six or more creature cards in your graveyard.
        ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new GateToTheAfterlifeEffect(), new GenericManaCost(2),
                new CardsInControllerGraveCondition(6, new FilterCreatureCard())
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public GateToTheAfterlife(final GateToTheAfterlife card) {
        super(card);
    }

    @Override
    public GateToTheAfterlife copy() {
        return new GateToTheAfterlife(this);
    }
}

class GateToTheAfterlifeEffect extends OneShotEffect {

    static private String cardName = "God-Pharaoh's Gift";

    public GateToTheAfterlifeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your graveyard, hand, and/or library for a card named "
                + cardName
                + " and put it onto the battlefield. If you search your library this way, shuffle it";
    }

    public GateToTheAfterlifeEffect(final GateToTheAfterlifeEffect effect) {
        super(effect);
    }

    @Override
    public GateToTheAfterlifeEffect copy() {
        return new GateToTheAfterlifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card named " + cardName);
        filter.add(new NamePredicate(cardName));
        Card card = null;
        // Graveyard check
        if (controller.chooseUse(Outcome.Benefit, "Do you want to search your graveyard for " + cardName + "?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(1, 1, filter, true);
            if (controller.choose(outcome, controller.getGraveyard(), target, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Hand check
        if (card == null && controller.chooseUse(Outcome.Benefit, "Do you want to search your hand for " + cardName + "?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(0, 1, filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Library check
        boolean librarySearched = false;
        if (card == null && controller.chooseUse(Outcome.Benefit, "Do you want to search your library for " + cardName + "?", source, game)) {
            librarySearched = true;
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            controller.shuffleLibrary(source, game);
        }
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        if (librarySearched) {
            controller.shuffleLibrary(source, game);
        }
        return true;
    }
}
