package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NavigationOrb extends CardImpl {

    public NavigationOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, {T}, Sacrifice Navigation Orb: Search your library for up to two basic land cards and/or Gate cards, reveal those cards, put one onto the battlefield tapped and the other into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(new NavigationOrbEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private NavigationOrb(final NavigationOrb card) {
        super(card);
    }

    @Override
    public NavigationOrb copy() {
        return new NavigationOrb(this);
    }
}

class NavigationOrbEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("basic land cards and/or Gate cards");

    static {
        filter.add(Predicates.or(Predicates.and(
                CardType.LAND.getPredicate(),
                SuperType.BASIC.getPredicate()
        ), SubType.GATE.getPredicate()));
    }

    NavigationOrbEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to two basic land cards and/or Gate cards, reveal those cards, " +
                "put one onto the battlefield tapped and the other into your hand, then shuffle";
    }

    private NavigationOrbEffect(final NavigationOrbEffect effect) {
        super(effect);
    }

    @Override
    public NavigationOrbEffect copy() {
        return new NavigationOrbEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, filter);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .forEach(cards::add);
        player.revealCards(source, cards, game);
        Card card;
        switch (cards.size()) {
            case 0:
                player.shuffleLibrary(source, game);
                return true;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard targetCard = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD);
                targetCard.withChooseHint("To put onto the battlefield");
                player.choose(outcome, cards, targetCard, source, game);
                card = cards.get(targetCard.getFirstTarget(), game);
        }
        cards.remove(card);
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
