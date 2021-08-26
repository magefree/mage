
package mage.cards.j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class JumboImp extends CardImpl {

    public JumboImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        //  Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Jumbo Imp enters the battlefield, roll a six-sided die. Jumbo Imp enters the battlefield with a number of +1/+1 counters on it equal to the result.
        this.addAbility(new EntersBattlefieldAbility(new JumboImpEffect(CounterType.P1P1.createInstance())));

        // At the beginning of your upkeep, roll a six-sided die and put a number of +1/+1 counters on Jumbo Imp equal to the result. 
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new JumboImpAddCountersEffect(), TargetController.YOU, false);
        this.addAbility(ability2);

        // At the beginning of your end step, roll a six-sided die and remove a number of +1/+1 counters from Jumbo Imp equal to the result.
        Ability ability3 = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new JumboImpRemoveCountersEffect(), TargetController.YOU, null, false);
        this.addAbility(ability3);
    }

    private JumboImp(final JumboImp card) {
        super(card);
    }

    @Override
    public JumboImp copy() {
        return new JumboImp(this);
    }
}

class JumboImpEffect extends EntersBattlefieldWithXCountersEffect {

    public JumboImpEffect(Counter counter) {
        super(counter);
    }

    public JumboImpEffect(EntersBattlefieldWithXCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game, appliedEffects);
            return super.apply(game, source);
        }
        return false;
    }

    @Override
    public EntersBattlefieldWithXCountersEffect copy() {
        return new JumboImpEffect(this);
    }

}

class JumboImpAddCountersEffect extends OneShotEffect {

    public JumboImpAddCountersEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die and put a number of +1/+1 counters on {this} equal to the result";
    }

    public JumboImpAddCountersEffect(final JumboImpAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public JumboImpAddCountersEffect copy() {
        return new JumboImpAddCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}

class JumboImpRemoveCountersEffect extends OneShotEffect {

    public JumboImpRemoveCountersEffect() {
        super(Outcome.Detriment);
        this.staticText = "roll a six-sided die and remove a number of +1/+1 counters on {this} equal to the result";
    }

    public JumboImpRemoveCountersEffect(final JumboImpRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public JumboImpRemoveCountersEffect copy() {
        return new JumboImpRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            permanent.removeCounters(CounterType.P1P1.createInstance(amount), source, game);
            return true;
        }
        return false;
    }
}
