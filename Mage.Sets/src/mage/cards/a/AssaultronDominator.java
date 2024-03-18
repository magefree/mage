package mage.cards.a;

import java.util.*;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Cguy7777
 */
public final class AssaultronDominator extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("artifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public AssaultronDominator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Assaultron Dominator enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever an artifact creature you control attacks, you may pay {E}. If you do, put your choice of a +1/+1, first strike, or trample counter on that creature.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new DoIfCostPaid(new AssaultronDominatorEffect(), new PayEnergyCost(1)), false, filter, true));
    }

    private AssaultronDominator(final AssaultronDominator card) {
        super(card);
    }

    @Override
    public AssaultronDominator copy() {
        return new AssaultronDominator(this);
    }
}

class AssaultronDominatorEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>(Arrays.asList("+1/+1", "First strike", "Trample"));

    AssaultronDominatorEffect() {
        super(Outcome.BoostCreature);
        staticText = "put your choice of a +1/+1, first strike, or trample counter on that creature";
    }

    protected AssaultronDominatorEffect(AssaultronDominatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || creature == null) {
            return false;
        }

        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose +1/+1, first strike, or trample counter");
        choice.setChoices(choices);
        player.choose(outcome, choice, game);

        String chosen = choice.getChoice();

        CounterType counterType;
        switch (chosen) {
            case "+1/+1":
                counterType = CounterType.P1P1;
                break;
            case "First strike":
                counterType = CounterType.FIRST_STRIKE;
                break;
            case "Trample":
                counterType = CounterType.TRAMPLE;
                break;
            default:
                return false;
        }

        return creature.addCounters(counterType.createInstance(), source, game);
    }

    @Override
    public AssaultronDominatorEffect copy() {
        return new AssaultronDominatorEffect(this);
    }
}
