package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PointTheWay extends CardImpl {

    public PointTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {3}{G}, Sacrifice this enchantment: Search your library for up to X basic land cards, where X is your speed. Put those cards onto the battlefield tapped, then shuffle.
        Ability ability = new SimpleActivatedAbility(new PointTheWayEffect(), new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PointTheWay(final PointTheWay card) {
        super(card);
    }

    @Override
    public PointTheWay copy() {
        return new PointTheWay(this);
    }
}

class PointTheWayEffect extends OneShotEffect {

    PointTheWayEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, where X is your speed. " +
                "Put those cards onto the battlefield tapped, then shuffle";
    }

    private PointTheWayEffect(final PointTheWayEffect effect) {
        super(effect);
    }

    @Override
    public PointTheWayEffect copy() {
        return new PointTheWayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, player.getSpeed(), StaticFilters.FILTER_CARD_BASIC_LANDS
        );
        player.searchLibrary(target, source, game);
        Set<Card> cards = target
                .getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .collect(Collectors.toSet());
        player.moveCards(
                cards, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}
