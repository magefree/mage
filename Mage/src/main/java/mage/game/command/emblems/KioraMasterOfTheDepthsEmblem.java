package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author spjspj
 */
public final class KioraMasterOfTheDepthsEmblem extends Emblem {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures");

    public KioraMasterOfTheDepthsEmblem() {
        super("Emblem Kiora");

        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.COMMAND,
                new KioraFightEffect(), filter, true, SetTargetPointer.PERMANENT,
                "Whenever a creature enters the battlefield under your control, you may have it fight target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        this.getAbilities().add(ability);
    }

    private KioraMasterOfTheDepthsEmblem(final KioraMasterOfTheDepthsEmblem card) {
        super(card);
    }

    @Override
    public KioraMasterOfTheDepthsEmblem copy() {
        return new KioraMasterOfTheDepthsEmblem(this);
    }
}

class KioraFightEffect extends OneShotEffect {

    KioraFightEffect() {
        super(Outcome.Damage);
    }

    KioraFightEffect(final KioraFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature(game)
                && target.isCreature(game)) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public KioraFightEffect copy() {
        return new KioraFightEffect(this);
    }
}
