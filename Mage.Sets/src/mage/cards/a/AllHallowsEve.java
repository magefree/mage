
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class AllHallowsEve extends CardImpl {

    public AllHallowsEve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Exile All Hallow's Eve with two scream counters on it.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
        Effect effect = new AddCountersSourceEffect(CounterType.SCREAM.createInstance(), new StaticValue(2), true, true);
        effect.setText("with 2 scream counters on it");
        this.getSpellAbility().addEffect(effect);

        // At the beginning of your upkeep, if All Hallow's Eve is exiled with a scream counter on it, remove a scream counter from it. If there are no more scream counters on it, put it into your graveyard and each player returns all creature cards from their graveyard to the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.EXILED, new AllHallowsEveEffect(), TargetController.YOU, false));

    }

    public AllHallowsEve(final AllHallowsEve card) {
        super(card);
    }

    @Override
    public AllHallowsEve copy() {
        return new AllHallowsEve(this);
    }
}

class AllHallowsEveEffect extends OneShotEffect {

    public AllHallowsEveEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "if {this} is exiled with a scream counter on it, remove a scream counter from it. If there are no more scream counters on it, put it into your graveyard and each player returns all creature cards from their graveyard to the battlefield";
    }

    public AllHallowsEveEffect(final AllHallowsEveEffect effect) {
        super(effect);
    }

    @Override
    public AllHallowsEveEffect copy() {
        return new AllHallowsEveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card allHallowsEve = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (allHallowsEve != null
                && controller != null
                && game.getExile().getCard(allHallowsEve.getId(), game) != null) {
            allHallowsEve.getCounters(game).removeCounter(CounterType.SCREAM, 1);
            if (allHallowsEve.getCounters(game).getCount(CounterType.SCREAM) == 0) {
                allHallowsEve.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.moveCards(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game), Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
