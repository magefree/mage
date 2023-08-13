package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class AgentsToolkit extends CardImpl {
    public AgentsToolkit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}{U}");

        this.subtype.add(SubType.CLUE);

        // Agent’s Toolkit enters the battlefield with a +1/+1 counter, a flying counter, a deathtouch counter, and a shield counter on it.
        Ability counterETBAbility = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)).setText("with a +1/+1 counter"));
        counterETBAbility.addEffect(new AddCountersSourceEffect(CounterType.FLYING.createInstance(1)).setText("a flying counter").concatBy(","));
        counterETBAbility.addEffect(new AddCountersSourceEffect(CounterType.DEATHTOUCH.createInstance(1)).setText("a deathtouch counter").concatBy(","));
        counterETBAbility.addEffect(new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)).setText("and a shield counter on it").concatBy(","));
        this.addAbility(counterETBAbility);

        // Whenever a creature enters the battlefield under your control,
        // you may move a counter from Agent’s Toolkit onto that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AgentToolkitMoveCounterEffect(),
                StaticFilters.FILTER_PERMANENT_A_CREATURE
        ));

        // {2}, Sacrifice Agent’s Toolkit: Draw a card.
        Ability drawAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        drawAbility.addCost(new SacrificeSourceCost());
        this.addAbility(drawAbility);
    }

    private AgentsToolkit(final AgentsToolkit card) {
        super(card);
    }

    @Override
    public AgentsToolkit copy() {
        return new AgentsToolkit(this);
    }
}

class AgentToolkitMoveCounterEffect extends OneShotEffect {

    public AgentToolkitMoveCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may move a counter from {this} onto that creature";
    }

    private AgentToolkitMoveCounterEffect(final AgentToolkitMoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent agentsToolkitPermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (agentsToolkitPermanent == null || controller == null) {
            return false;
        }
        Object enteringObject = this.getValue("permanentEnteringBattlefield");
        if (!(enteringObject instanceof Permanent)) {
            return false;
        }
        Permanent enteringCreature = (Permanent) enteringObject;

        Choice moveCounterChoice = new ChoiceImpl(false);
        Set<String> possibleCounterNames = new LinkedHashSet<>(agentsToolkitPermanent.getCounters(game).keySet());
        moveCounterChoice.setMessage("Choose counter to move");
        moveCounterChoice.setChoices(possibleCounterNames);

        if (controller.choose(outcome, moveCounterChoice, game) && possibleCounterNames.contains(moveCounterChoice.getChoice())) {
            String counterName = moveCounterChoice.getChoice();
            CounterType counterType = CounterType.findByName(counterName);
            if (counterType == null) {
                return false;
            }
            agentsToolkitPermanent.removeCounters(counterType.getName(), 1, source, game);
            enteringCreature.addCounters(counterType.createInstance(), source, game);
        }
        return true;
    }

    @Override
    public AgentToolkitMoveCounterEffect copy() {
        return new AgentToolkitMoveCounterEffect(this);
    }
}