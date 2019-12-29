package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalierOfThorns extends CardImpl {

    private static final FilterCard filter = new FilterCard("another card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CavalierOfThorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Cavalier of Thorns enters the battlefield, reveal the top five cards of your library. You may put a land card from among them onto the battlefield. Put the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CavalierOfThornsEffect()));

        // When Cavalier of Thorns dies, you may exile it. If you do, put another target card from your graveyard on top of your library.
        Ability ability = new DiesTriggeredAbility(new DoIfCostPaid(
                new PutOnLibraryTargetEffect(true), new ExileSourceFromGraveCost()
        ).setText("you may exile it. If you do, put another target card from your graveyard on top of your library."));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private CavalierOfThorns(final CavalierOfThorns card) {
        super(card);
    }

    @Override
    public CavalierOfThorns copy() {
        return new CavalierOfThorns(this);
    }
}

class CavalierOfThornsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterLandCard("land card to put on the battlefield");

    CavalierOfThornsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top five cards of your library. " +
                "Put a land card from among them onto the battlefield and the rest into your graveyard.";
    }

    private CavalierOfThornsEffect(final CavalierOfThornsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        if (cards.isEmpty()) {
            return true;
        }
        controller.revealCards(source, cards, game);
        TargetCard target = new TargetCard(1, 1, Zone.LIBRARY, filter);
        if (cards.getCards(game).stream().anyMatch(Card::isLand)
                && controller.choose(Outcome.PutCardInPlay, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }

    @Override
    public CavalierOfThornsEffect copy() {
        return new CavalierOfThornsEffect(this);
    }
}
