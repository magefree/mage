package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.*;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class SlicerHiredMuscle extends TransformingDoubleFacedCard {

    public SlicerHiredMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{4}{R}",
                "Slicer, High-Speed Antagonist",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "R"
        );

        // Slicer, Hired Muscle
        this.getLeftHalfCard().setPT(3, 4);

        // More Than Meets the Eye {2}{R}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{R}"));

        // Double strike
        this.getLeftHalfCard().addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // At the beginning of each opponent's upkeep, you may have that player gain
        // control of Slicer until end of turn. If you do, untap Slicer, goad it, and it
        // can't be sacrificed this turn. If you don't, convert it.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.OPPONENT, new SlicerHiredMuscleUpkeepEffect(),
                false));

        // Slicer, High-Speed Antagonist
        this.getRightHalfCard().setPT(3, 2);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Slicer deals combat damage to a player, convert it at end of combat.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(new TransformSourceEffect()))
                        .setText("convert it at end of combat"),
                false));
    }

    private SlicerHiredMuscle(final SlicerHiredMuscle card) {
        super(card);
    }

    @Override
    public SlicerHiredMuscle copy() {
        return new SlicerHiredMuscle(this);
    }
}

class SlicerHiredMuscleUpkeepEffect extends OneShotEffect {

    SlicerHiredMuscleUpkeepEffect() {
        super(Outcome.GainControl);
        staticText = "you may have that player gain control of {this} until end of turn. If you do, untap {this}, goad it, and it can't be sacrificed this turn. If you don't, convert it.";
    }

    private SlicerHiredMuscleUpkeepEffect(final SlicerHiredMuscleUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public SlicerHiredMuscleUpkeepEffect copy() {
        return new SlicerHiredMuscleUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(sourcePermanent.getControllerId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));

        if (player == null || newController == null || sourcePermanent == null) {
            return false;
        }

        if (player.chooseUse(this.getOutcome(), source.getRule(), source, game)) {

            // Gain control
            game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn, false, newController.getId())
                    .setTargetPointer(new FixedTarget(sourcePermanent, game)), source);

            // process action so that untap effects the new controller
            game.processAction();

            // Untap
            sourcePermanent.untap(game);

            // Goad
            game.addEffect(new GoadTargetEffect()
                            .setDuration(Duration.EndOfTurn)
                            .setTargetPointer(new FixedTarget(sourcePermanent, game)),
                    source);

            // Can't be sacrificed
            game.addEffect(new GainAbilityTargetEffect(
                    new SimpleStaticAbility(
                            new CantBeSacrificedSourceEffect().setText("This creature can't be sacrificed")),
                    Duration.EndOfTurn).setTargetPointer(new FixedTarget(sourcePermanent, game)), source);

        } else {
            new TransformSourceEffect().apply(game, source);
        }
        return true;
    }
}
