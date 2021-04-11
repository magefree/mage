package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class CopySourceSpellEffect extends OneShotEffect {

    private final int amount;

    public CopySourceSpellEffect() {
        this(1);
    }

    public CopySourceSpellEffect(int amount) {
        super(Outcome.Benefit);
        staticText = "copy {this}";
        this.amount = amount;
    }

    private CopySourceSpellEffect(final CopySourceSpellEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            spell = game.getSpellOrLKIStack(source.getSourceId());
        }
        if (controller == null || spell == null) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, amount);
        return true;
    }

    @Override
    public CopySourceSpellEffect copy() {
        return new CopySourceSpellEffect(this);
    }
}
