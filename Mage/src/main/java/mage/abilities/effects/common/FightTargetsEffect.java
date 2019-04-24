
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
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
            // only if both targets are legal the effect will be applied
            if (source.getTargets().get(0).isLegal(source, game) && source.getTargets().get(1).isLegal(source, game)) {
                Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
                Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                // 20110930 - 701.10
                if (creature1 != null && creature2 != null) {
                    if (creature1.isCreature() && creature2.isCreature()) {
                        return creature1.fight(creature2, source, game);
                    }
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
        return "Target " + mode.getTargets().get(0).getTargetName() + " fights another target " + mode.getTargets().get(1).getTargetName();
    }

}
