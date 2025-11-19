package mage.cards.o;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.cards.TransformingDoubleFacedCardHalf;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jbureau88
 */
public final class OptimusPrimeHero extends TransformingDoubleFacedCard {

    public OptimusPrimeHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{3}{U}{R}{W}",
                "Optimus Prime, Autobot Leader",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "WUR");

        this.getLeftHalfCard().setPT(4, 8);
        this.getRightHalfCard().setPT(6, 8);

        // More Than Meets the Eye {2}{U}{R}{W}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{U}{R}{W}"));

        // At the beginning of each end step, bolster 1.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.ANY, new BolsterEffect(1), false));

        // When Optimus Prime dies, return it to the battlefield converted under its owner’s control.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new OptimusPrimeHeroEffect()));

        // Optimus Prime, Autobot Leader

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever you attack, bolster 2. The chosen creature gains trample until end of turn. When that creature deals combat damage to a player this turn, convert Optimus Prime.
        this.getRightHalfCard().addAbility(new AttacksWithCreaturesTriggeredAbility(new BolsterEffect(2)
                .withAdditionalEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()))
                .withAdditionalEffect(new OptimusPrimeAutobotLeaderEffect())
                .setText("bolster 2. The chosen creature gains trample until end of turn. When that creature deals combat damage to a player this turn, convert {this}"),
                1));
    }

    private OptimusPrimeHero(final OptimusPrimeHero card) {
        super(card);
    }

    @Override
    public OptimusPrimeHero copy() {
        return new OptimusPrimeHero(this);
    }
}

class OptimusPrimeHeroEffect extends OneShotEffect {

    OptimusPrimeHeroEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield converted under its owner's control.";
    }

    private OptimusPrimeHeroEffect(final OptimusPrimeHeroEffect effect) {
        super(effect);
    }

    @Override
    public OptimusPrimeHeroEffect copy() {
        return new OptimusPrimeHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return true;
        }
        Card backSide = ((TransformingDoubleFacedCardHalf) card).getOtherSide();
        if (backSide == null) {
            return true;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + backSide.getId(), true);
        controller.moveCards(backSide, Zone.BATTLEFIELD, source, game);
        return true;
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
