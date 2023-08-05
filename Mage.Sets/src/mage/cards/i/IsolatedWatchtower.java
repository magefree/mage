package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class IsolatedWatchtower extends CardImpl {

    public IsolatedWatchtower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Scry 1, then you may reveal the top card of your library. If a basic land card is revealed this way, put it onto the battlefield tapped. Activate this ability only if an opponent controls at least two more lands than you.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new IsolatedWatchtowerEffect(),
                new GenericManaCost(2),
                new IsolatedWatchtowerCondition()
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private IsolatedWatchtower(final IsolatedWatchtower card) {
        super(card);
    }

    @Override
    public IsolatedWatchtower copy() {
        return new IsolatedWatchtower(this);
    }
}

class IsolatedWatchtowerEffect extends OneShotEffect {

    public IsolatedWatchtowerEffect() {
        super(Outcome.Benefit);
        this.staticText = "scry 1, then you may reveal the top card "
                + "of your library. If a basic land card is revealed this way, "
                + "put it onto the battlefield tapped";
    }

    public IsolatedWatchtowerEffect(final IsolatedWatchtowerEffect effect) {
        super(effect);
    }

    @Override
    public IsolatedWatchtowerEffect copy() {
        return new IsolatedWatchtowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.scry(1, source, game);
        if (!player.chooseUse(
                outcome, "Reveal the top card of your library?", source, game
        )) {
            return true;
        }
        Card card = player.getLibrary().getFromTop(game);
        player.revealCards(source, new CardsImpl(card), game);
        if (card.isBasic(game) && card.isLand(game)) {
            player.moveCards(
                    card, Zone.BATTLEFIELD, source,
                    game, true, false, true, null
            );
        }
        return true;
    }
}

class IsolatedWatchtowerCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        int numLands = game.getBattlefield().countAll(
                StaticFilters.FILTER_LAND, source.getControllerId(), game
        );
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (numLands + 2 <= game.getBattlefield().countAll(
                    StaticFilters.FILTER_LAND, opponentId, game
            )) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent controls at least two more lands than you";
    }
}
