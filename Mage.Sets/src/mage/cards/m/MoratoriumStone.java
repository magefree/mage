package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MoratoriumStone extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland card from a graveyard");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public MoratoriumStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {tap}: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // {2}{W}{B}, {tap}, Sacrifice Moratorium Stone: Exile target nonland card from a graveyard, all other cards from graveyards with the same name as that card, and all permanents with that name.
        ability = new SimpleActivatedAbility(new MoratoriumStoneEffect(), new ManaCostsImpl<>("{2}{W}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private MoratoriumStone(final MoratoriumStone card) {
        super(card);
    }

    @Override
    public MoratoriumStone copy() {
        return new MoratoriumStone(this);
    }
}

class MoratoriumStoneEffect extends OneShotEffect {

    MoratoriumStoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target nonland card from a graveyard, all other cards from graveyards with the same name as that card, and all permanents with that name.";
    }

    private MoratoriumStoneEffect(final MoratoriumStoneEffect effect) {
        super(effect);
    }

    @Override
    public MoratoriumStoneEffect copy() {
        return new MoratoriumStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        Set<Card> cards = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(c -> c.sharesName(card, game))
                .collect(Collectors.toSet());
        cards.add(card);
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(card, game))
                .forEach(cards::add);
        return player.moveCards(card, Zone.EXILED, source, game);
    }
}
