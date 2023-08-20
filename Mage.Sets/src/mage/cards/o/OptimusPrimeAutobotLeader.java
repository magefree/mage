package mage.cards.o;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class OptimusPrimeAutobotLeader extends CardImpl {

    public OptimusPrimeAutobotLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you attack, bolster 2. The chosen creature gains trample until end of turn. When that creature deals combat damage to a player this turn, convert Optimus Prime.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new BolsterEffect(2)
                .withAdditionalEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()))
                .withAdditionalEffect(new OptimusPrimeAutobotLeaderEffect())
                .setText("bolster 2. The chosen creature gains trample until end of turn. When that creature deals combat damage to a player this turn, convert {this}"),
                1));

        // Transform Ability
        this.addAbility(new TransformAbility());
    }

    private OptimusPrimeAutobotLeader(final OptimusPrimeAutobotLeader card) {
        super(card);
    }

    @Override
    public OptimusPrimeAutobotLeader copy() {
        return new OptimusPrimeAutobotLeader(this);
    }
}

class OptimusPrimeAutobotLeaderEffect extends OneShotEffect {

    OptimusPrimeAutobotLeaderEffect() {
        super(Outcome.Transform);
    }

    private OptimusPrimeAutobotLeaderEffect(final OptimusPrimeAutobotLeaderEffect effect) {
        super(effect);
    }

    @Override
    public OptimusPrimeAutobotLeaderEffect copy() {
        return new OptimusPrimeAutobotLeaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new OptimusPrimeAutobotLeaderDelayedTriggeredAbility(new MageObjectReference(creature, game)), source);
        return true;
    }

}

class OptimusPrimeAutobotLeaderDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    OptimusPrimeAutobotLeaderDelayedTriggeredAbility(MageObjectReference mor) {
        super(new TransformSourceEffect().setText("convert {this}"), Duration.EndOfTurn);
        this.mor = mor;
        setTriggerPhrase("When that creature deals combat damage to a player this turn, ");
    }

    private OptimusPrimeAutobotLeaderDelayedTriggeredAbility(final OptimusPrimeAutobotLeaderDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public OptimusPrimeAutobotLeaderDelayedTriggeredAbility copy() {
        return new OptimusPrimeAutobotLeaderDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            return mor.refersTo(permanent, game);
        }
        return false;
    }

}
