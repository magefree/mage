package mage.cards.r;

import java.util.Collection;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Callumvl
 */
public final class RepurposingBay extends CardImpl {

    public RepurposingBay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {2}, {T}, Sacrifice another artifact: Search your library for an artifact card with mana value equal to 1 plus the sacrificed artifact's mana value, put that card onto the battlefield, then shuffle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new RepurposingBayEffect(), new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT));
        this.addAbility(ability);
    }

    private RepurposingBay(final RepurposingBay card) {
        super(card);
    }

    @Override
    public RepurposingBay copy() {
        return new RepurposingBay(this);
    }
}

class RepurposingBayEffect extends OneShotEffect {

    RepurposingBayEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for an artifact card with mana value equal to 1 plus the " +
                "sacrificed artifact's mana value, put that card onto the battlefield, then shuffle";
    }

    private RepurposingBayEffect(final RepurposingBayEffect effect) {
        super(effect);
    }

    @Override
    public RepurposingBayEffect copy() {
        return new RepurposingBayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sacrificed = source
                .getCosts()
                .stream()
                .filter(SacrificeTargetCost.class::isInstance)
                .map(SacrificeTargetCost.class::cast)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(null);
        if (player == null || sacrificed == null) {
            return false;
        }
        FilterCard filterCard = new FilterArtifactCard(
                "artifact card with mana value " + (sacrificed.getManaValue() + 1)
        );
        filterCard.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, sacrificed.getManaValue() + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filterCard);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
