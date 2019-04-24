
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 *
 */
public class CopyTargetSpellEffect extends OneShotEffect {

    private final boolean useController;
    private final boolean useLKI;

    public CopyTargetSpellEffect() {
        this(false);
    }

    public CopyTargetSpellEffect(boolean useLKI) {
        this(false, useLKI);
    }

    public CopyTargetSpellEffect(boolean useController, boolean useLKI) {
        super(Outcome.Copy);
        this.useController = useController;
        this.useLKI = useLKI;
    }

    public CopyTargetSpellEffect(final CopyTargetSpellEffect effect) {
        super(effect);
        this.useLKI = effect.useLKI;
        this.useController = effect.useController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell;
        if (useLKI) {
            spell = game.getSpellOrLKIStack(targetPointer.getFirst(game, source));
        } else {
            spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        }
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
        }
        if (spell != null) {
            StackObject newStackObject = spell.createCopyOnStack(game, source, useController ? spell.getControllerId() : source.getControllerId(), true);
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && newStackObject instanceof Spell) {
                String activateMessage = ((Spell) newStackObject).getActivatedMessage(game);
                if (activateMessage.startsWith(" casts ")) {
                    activateMessage = activateMessage.substring(6);
                }
                if (!game.isSimulation()) {
                    game.informPlayers(player.getLogName() + activateMessage);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CopyTargetSpellEffect copy() {
        return new CopyTargetSpellEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("copy target ").append(mode.getTargets().get(0).getTargetName()).append(". You may choose new targets for the copy");
        return sb.toString();
    }
}
