package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 * @author TheElk801
 */
public class DemonstrateAbility extends CastSourceTriggeredAbility {

    public DemonstrateAbility() {
        super(new DemonstrateEffect(), true);
    }

    private DemonstrateAbility(final DemonstrateAbility ability) {
        super(ability);
    }

    @Override
    public DemonstrateAbility copy() {
        return new DemonstrateAbility(this);
    }

    @Override
    public String getRule() {
        return "Demonstrate <i>(When you cast this spell, you may copy it. If you do, " +
                "choose an opponent to also copy it. Players may choose new targets for their copies.)</i>";
    }
}

class DemonstrateEffect extends OneShotEffect {

    DemonstrateEffect() {
        super(Outcome.Benefit);
    }

    private DemonstrateEffect(final DemonstrateEffect effect) {
        super(effect);
    }

    @Override
    public DemonstrateEffect copy() {
        return new DemonstrateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true);
        TargetOpponent target = new TargetOpponent(true);
        controller.choose(outcome, target, source, game);
        if (game.getPlayer(target.getFirstTarget()) != null) {
            spell.createCopyOnStack(game, source, target.getFirstTarget(), true);
        }
        return true;
    }
}
