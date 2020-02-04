package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FightTargetsEffect extends OneShotEffect {

    public FightTargetsEffect() {
        super(Outcome.Damage);
    }

    public FightTargetsEffect(String effectText) {
        this();
        this.staticText = effectText;
    }

    public FightTargetsEffect(final FightTargetsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            UUID target1Id = null;
            UUID target2Id = null;
            // first target is in target pointer, second target is a normal target
            if (source.getTargets().size() < 2) {
                if (!source.getTargets().get(0).isLegal(source, game)) {
                    return false;
                }
                target1Id = getTargetPointer().getFirst(game, source);
                target2Id = source.getTargets().getFirstTarget();
                if (target1Id == target2Id) {
                    return false;
                }
                // two normal targets available, only if both targets are legal the effect will be applied
            } else if (source.getTargets().get(0).isLegal(source, game) && source.getTargets().get(1).isLegal(source, game)) {
                target1Id = source.getTargets().get(0).getFirstTarget();
                target2Id = source.getTargets().get(1).getFirstTarget();
            }
            Permanent creature1 = game.getPermanent(target1Id);
            Permanent creature2 = game.getPermanent(target2Id);
            // 20110930 - 701.10
            if (creature1 != null && creature2 != null) {
                if (creature1.isCreature() && creature2.isCreature()) {
                    return creature1.fight(creature2, source, game);
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(card.getName() + " has been fizzled.");
            }
        }

        return false;
    }

    @Override
    public FightTargetsEffect copy() {
        return new FightTargetsEffect(this);

    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;

        }
        return "Target " + mode
                .getTargets().get(0).getTargetName() + " fights another target " + mode
                .getTargets().get(1).getTargetName();
    }

}