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
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
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
        ).setText("with two scream counters on it"));

        // At the beginning of your upkeep, if All Hallow's Eve is exiled with a scream counter on it, remove a scream counter from it.
        // If there are no more scream counters on it, put it into your graveyard and each player returns all creature cards from their graveyard to the battlefield.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.EXILED,
                new RemoveCounterSourceEffect(CounterType.SCREAM.createInstance(1)),
                TargetController.YOU,
                false
        );
        ability.addEffect(new AllHallowsEveEffect());
        this.addAbility(ability);
    }

    private AllHallowsEve(final AllHallowsEve card) {
        super(card);
    }

    @Override
    public AllHallowsEve copy() {
        return new AllHallowsEve(this);
    }
}

class AllHallowsEveEffect extends OneShotEffect {

    AllHallowsEveEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "If there are no more scream counters on it, put it into your graveyard " +
                "and each player returns all creature cards from their graveyard to the battlefield.";
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
        Card allHallowsEveCard = (Card) source.getSourceObject(game);
        if (allHallowsEveCard == null)  { return false; }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        if (allHallowsEveCard.getCounters(game).getCount(CounterType.SCREAM) > 0) { return false; }

        controller.moveCards(allHallowsEveCard, Zone.GRAVEYARD, source, game);
        Cards allCreatureCardsInGraveyards = new CardsImpl();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(card1 -> card1.isCreature(game))
                .forEach(allCreatureCardsInGraveyards::add);

        controller.moveCards(allCreatureCardsInGraveyards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }
}
