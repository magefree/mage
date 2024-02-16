package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BearToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PathToTheWorldTree extends CardImpl {

    public PathToTheWorldTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // When Path to the World Tree enters the battlefield, search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        )));

        // {2}{W}{U}{B}{R}{G}, Sacrifice Path to the World Tree: You gain 2 life and draw two cards. Target opponent loses 2 life. Path to the World Tree deals 2 damage to up to one target creature. You create a 2/2 green Bear creature token.
        Ability ability = new SimpleActivatedAbility(
                new PathToTheWorldTreeEffect(), new ManaCostsImpl<>("{2}{W}{U}{B}{R}{G}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private PathToTheWorldTree(final PathToTheWorldTree card) {
        super(card);
    }

    @Override
    public PathToTheWorldTree copy() {
        return new PathToTheWorldTree(this);
    }
}

class PathToTheWorldTreeEffect extends OneShotEffect {

    PathToTheWorldTreeEffect() {
        super(Outcome.Benefit);
        staticText = "You gain 2 life and draw two cards. Target opponent loses 2 life. " +
                "{this} deals 2 damage to up to one target creature. You create a 2/2 green Bear creature token.";
    }

    private PathToTheWorldTreeEffect(final PathToTheWorldTreeEffect effect) {
        super(effect);
    }

    @Override
    public PathToTheWorldTreeEffect copy() {
        return new PathToTheWorldTreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(2, game, source);
            controller.drawCards(2, source, game);
        }
        for (UUID targetId : source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())) {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                player.loseLife(2, game, source, false);
            }
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.damage(2, source.getSourceId(), source, game);
            }
        }
        new BearToken().putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }
}
