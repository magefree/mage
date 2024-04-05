package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.functions.StackObjectCopyApplier;

/**
 * @author TheElk801
 */
public class CopySourceSpellEffect extends OneShotEffect {

    private final int amount;

    private final StackObjectCopyApplier applier;
    public CopySourceSpellEffect(StackObjectCopyApplier applier) {
        this(1, applier);
    }
    public CopySourceSpellEffect() {
        this(1, null);
    }

    public CopySourceSpellEffect(int amount) {
        this(amount, null);
    }
    public CopySourceSpellEffect(int amount, StackObjectCopyApplier applier) {
        super(Outcome.Benefit);
        staticText = "copy {this}";
        this.amount = amount;
        this.applier = applier;
    }

    private CopySourceSpellEffect(final CopySourceSpellEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.applier = effect.applier;
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
        if(applier==null){
            spell.createCopyOnStack(game, source, source.getControllerId(), true, amount);
        } else {
            spell.createCopyOnStack(game, source, source.getControllerId(),true, amount, applier);
        }
        return true;
    }

    @Override
    public CopySourceSpellEffect copy() {
        return new CopySourceSpellEffect(this);
    }
}
