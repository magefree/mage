package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViviensArkbow extends CardImpl {

    public ViviensArkbow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        this.addSuperType(SuperType.LEGENDARY);

        // {X}, {T}, Discard a card: Look at the top X cards of your library. You may put a creature card with converted mana cost X or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(
                new ViviensArkbowEffect(), new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private ViviensArkbow(final ViviensArkbow card) {
        super(card);
    }

    @Override
    public ViviensArkbow copy() {
        return new ViviensArkbow(this);
    }
}

class ViviensArkbowEffect extends OneShotEffect {

    ViviensArkbowEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Look at the top X cards of your library. " +
                "You may put a creature card with mana value X or less " +
                "from among them onto the battlefield. Put the rest on the bottom of your library in a random order.";
    }

    private ViviensArkbowEffect(final ViviensArkbowEffect effect) {
        super(effect);
    }

    @Override
    public ViviensArkbowEffect copy() {
        return new ViviensArkbowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        player.lookAtCards(source, null, cards, game);

        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);

        if (player.choose(outcome, cards, target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                cards.remove(card);
            }
        }
        return player.putCardsOnBottomOfLibrary(cards, game, source, false);
    }
}