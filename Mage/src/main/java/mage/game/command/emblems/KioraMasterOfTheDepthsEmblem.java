package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author spjspj
 */
public final class KioraMasterOfTheDepthsEmblem extends Emblem {

    public KioraMasterOfTheDepthsEmblem() {
        super("Emblem Kiora");

        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.COMMAND,
                new KioraFightEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE, true, SetTargetPointer.PERMANENT);
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
        staticText = "you may have it fight target creature";
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
