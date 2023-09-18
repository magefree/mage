package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class ChargingCinderhorn extends CardImpl {

    public ChargingCinderhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each player's end step, if no creatures attacked this turn, put a fury counter on Charging Cinderhorn. Then Charging Cinderhorn deals damage equal to the number of fury counters on it to that player.
        ChargingCinderhornDamageTargetEffect effect = new ChargingCinderhornDamageTargetEffect();
        effect.setText("put a fury counter on {this}. Then {this} deals damage equal to the number of fury counters on it to that player");
        BeginningOfEndStepTriggeredAbility ability
                = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, new ChargingCinderhornCondition(), false);
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    private ChargingCinderhorn(final ChargingCinderhorn card) {
        super(card);
    }

    @Override
    public ChargingCinderhorn copy() {
        return new ChargingCinderhorn(this);
    }
}

class ChargingCinderhornCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAttackedThisTurnCreatures().isEmpty();
        }
        return true;
    }

    @Override
    public String toString() {
        return "if no creatures attacked this turn";
    }

}

class ChargingCinderhornDamageTargetEffect extends OneShotEffect {

    public ChargingCinderhornDamageTargetEffect() {
        super(Outcome.Damage);
    }

    private ChargingCinderhornDamageTargetEffect(final ChargingCinderhornDamageTargetEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent chargingCinderhoof = game.getPermanent(source.getSourceId());
        if (chargingCinderhoof != null) {
            chargingCinderhoof.addCounters(CounterType.FURY.createInstance(), source.getControllerId(), source, game);
        } else {
            chargingCinderhoof = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }

        if (chargingCinderhoof == null) {
            return false;
        }

        DynamicValue amount = new CountersSourceCount(CounterType.FURY);
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public ChargingCinderhornDamageTargetEffect copy() {
        return new ChargingCinderhornDamageTargetEffect(this);
    }
}
