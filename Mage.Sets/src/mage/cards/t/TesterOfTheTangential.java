package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.abilities.keyword.IncrementAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TesterOfTheTangential extends CardImpl {

    public TesterOfTheTangential(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Increment
        this.addAbility(new IncrementAbility());

        // At the beginning of combat on your turn, you may pay {X}. When you do, move X +1/+1 counters from this creature onto another target creature.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new TesterOfTheTangentialEffect()));
    }

    private TesterOfTheTangential(final TesterOfTheTangential card) {
        super(card);
    }

    @Override
    public TesterOfTheTangential copy() {
        return new TesterOfTheTangential(this);
    }
}

class TesterOfTheTangentialEffect extends OneShotEffect {

    TesterOfTheTangentialEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. When you do, move X +1/+1 counters from this creature onto another target creature";
    }

    private TesterOfTheTangentialEffect(final TesterOfTheTangentialEffect effect) {
        super(effect);
    }

    @Override
    public TesterOfTheTangentialEffect copy() {
        return new TesterOfTheTangentialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(Outcome.BoostCreature, "Pay {X}?", source, game)) {
            return false;
        }
        int xValue = controller.announceX(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source, true);
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        cost.add(new GenericManaCost(xValue));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new MoveCountersFromSourceToTargetEffect(CounterType.P1P1, xValue), false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
