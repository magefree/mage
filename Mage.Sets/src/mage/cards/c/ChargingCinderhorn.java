
package mage.cards.c;

import java.util.UUID;
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
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
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

    public ChargingCinderhorn(final ChargingCinderhorn card) {
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

    public ChargingCinderhornDamageTargetEffect(ChargingCinderhornDamageTargetEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent chargingCinderhoof = game.getPermanent(source.getSourceId());
        if (chargingCinderhoof != null) {
            chargingCinderhoof.addCounters(CounterType.FURY.createInstance(), source, game);
        } else {
            chargingCinderhoof = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }

        if (chargingCinderhoof == null) {
            return false;
        }

        DynamicValue amount = new CountersSourceCount(CounterType.FURY);
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public ChargingCinderhornDamageTargetEffect copy() {
        return new ChargingCinderhornDamageTargetEffect(this);
    }
}
