package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BorosBattleshaper extends CardImpl {

    public BorosBattleshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{W}");
        this.subtype.add(SubType.MINOTAUR, SubType.SOLDIER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of each combat, up to one target creature attacks or blocks this combat if able and up to one target creature can't attack or block this combat.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BorosBattleshaperEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetCreaturePermanent(0, 1).withChooseHint("attacks or blocks this combat if able"));
        ability.addTarget(new TargetCreaturePermanent(0, 1).withChooseHint("can't attack or block this combat"));
        this.addAbility(ability);

    }

    private BorosBattleshaper(final BorosBattleshaper card) {
        super(card);
    }

    @Override
    public BorosBattleshaper copy() {
        return new BorosBattleshaper(this);
    }

}

class BorosBattleshaperEffect extends OneShotEffect {

    BorosBattleshaperEffect() {
        super(Outcome.Benefit);
        this.staticText = "up to one target creature attacks or blocks this combat if able and up to one target creature can't attack or block this combat";
    }

    private BorosBattleshaperEffect(final BorosBattleshaperEffect effect) {
        super(effect);
    }

    @Override
    public BorosBattleshaperEffect copy() {
        return new BorosBattleshaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature1 != null) {
            if (game.getOpponents(creature1.getControllerId()).contains(game.getActivePlayerId())) {
                // Blocks
                ContinuousEffectImpl effect = new BlocksIfAbleTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature1.getId(), game));
                game.addEffect(effect, source);
            } else {
                // Attacks
                ContinuousEffectImpl effect = new AttacksIfAbleTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature1.getId(), game));
                game.addEffect(effect, source);
            }
        }
        Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature2 != null) {
            if (game.getOpponents(creature2.getControllerId()).contains(game.getActivePlayerId())) {
                // Blocks
                ContinuousEffectImpl effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature2.getId(), game));
                game.addEffect(effect, source);
            } else {
                // Attacks
                ContinuousEffectImpl effect = new CantAttackTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature2.getId(), game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
