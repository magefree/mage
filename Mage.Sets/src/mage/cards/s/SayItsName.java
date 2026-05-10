package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SayItsName extends CardImpl {

    private static final FilterCard filter = new FilterCard("other cards named Say Its Name from your graveyard");
    private static final FilterCard filterCreatureOrLand = new FilterCard("creature or land card from your graveyard");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new NamePredicate("Say Its Name"));
        filterCreatureOrLand.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public SayItsName(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Mill three cards. Then you may return a creature or land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(true,
                filterCreatureOrLand, PutCards.HAND).concatBy("Then"));

        // Exile this card and two other cards named Say Its Name from your graveyard: Search your graveyard, hand, and/or library for a card named Altanak, the Thrice-Called and put it onto the battlefield. If you search your library this way, shuffle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new SayItsNameSearchEffect(), new ExileSourceFromGraveCost().setText("exile this card")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, filter),
                "and two other cards named Say Its Name from your graveyard"));
        this.addAbility(ability);


    }

    private SayItsName(final SayItsName card) {
        super(card);
    }

    @Override
    public SayItsName copy() {
        return new SayItsName(this);
    }
}

class SayItsNameSearchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Altanak, the Thrice-Called");
    static {
        filter.add(new NamePredicate("Altanak, the Thrice-Called"));
    }

    SayItsNameSearchEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your graveyard, hand, and/or library for a card named Altanak, the Thrice-Called and put it onto the battlefield. If you search your library this way, shuffle.";
    }

    private SayItsNameSearchEffect(final SayItsNameSearchEffect effect) {
        super(effect);
    }

    @Override
    public SayItsNameSearchEffect copy() {
        return new SayItsNameSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = null;
        // Graveyard
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard?", source, game)) {
            // You can't fail to find the card in your graveyard because it's not hidden
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(1, 1, filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Hand
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your hand?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(0, 1, filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Library
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your library?", source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            controller.shuffleLibrary(source, game);
        }
        // Put on battlefield
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }

}
