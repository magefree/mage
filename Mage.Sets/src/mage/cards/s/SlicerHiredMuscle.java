package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.constants.*;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.players.Player;
import mage.abilities.common.SimpleStaticAbility;

/**
 * @author grimreap124
 */
public final class SlicerHiredMuscle extends CardImpl {

    public SlicerHiredMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT, CardType.CREATURE }, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.s.SlicerHighSpeedAntagonist.class;

        // More Than Meets the Eye {2}{R}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{R}"));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each opponent's upkeep, you may have that player gain
        // control of Slicer until end of turn. If you do, untap Slicer, goad it, and it
        // can't be sacrificed this turn. If you don't, convert it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SlicerHiredMuscleUpkeepEffect(),
                TargetController.OPPONENT, false, true));

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
