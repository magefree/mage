package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AllHallowsEve extends CardImpl {

    public AllHallowsEve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Exile All Hallow's Eve with two scream counters on it.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addEffect(new AddCountersSourceEffect(
                CounterType.SCREAM.createInstance(), StaticValue.get(2), true, true
        ).setText("with 2 scream counters on it"));

        // At the beginning of your upkeep, if All Hallow's Eve is exiled with a scream counter on it, remove a scream counter from it. If there are no more scream counters on it, put it into your graveyard and each player returns all creature cards from their graveyard to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(Zone.EXILED, new AllHallowsEveEffect(), TargetController.YOU, false), AllHallowsEveCondition.instance, "At the beginning of your upkeep, if {this} is exiled with a scream counter on it, remove a scream counter from it. If there are no more scream counters on it, put it into your graveyard and each player returns all creature cards from their graveyard to the battlefield."));
    }

    private AllHallowsEve(final AllHallowsEve card) {
        super(card);
    }

    @Override
    public AllHallowsEve copy() {
        return new AllHallowsEve(this);
    }
}

enum AllHallowsEveCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        return sourceObject != null
                && game.getState().getZone(source.getSourceId()) == Zone.EXILED
                && sourceObject instanceof Card
                && ((Card) sourceObject).getMainCard().getCounters(game).getCount(CounterType.SCREAM) > 0;
    }
}

class AllHallowsEveEffect extends OneShotEffect {

    AllHallowsEveEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private AllHallowsEveEffect(final AllHallowsEveEffect effect) {
        super(effect);
    }

    @Override
    public AllHallowsEveEffect copy() {
        return new AllHallowsEveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (card == null || controller == null) {
            return false;
        }
        controller.moveCards(card, Zone.GRAVEYARD, source, game);
        Cards cards = new CardsImpl();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(card1 -> card1.isCreature(game))
                .forEach(cards::add);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }
}
