package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.functions.StackObjectCopyApplier;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CopyTargetSpellEffect extends OneShotEffect {

    private final boolean useController;
    private final boolean useLKI;
    private String copyThatSpellName = "that spell";
    private final boolean chooseTargets;
    private final int amount;
    private final StackObjectCopyApplier applier;

    public CopyTargetSpellEffect() {
        this(false);
    }

    public CopyTargetSpellEffect(boolean useLKI) {
        this(false, useLKI);
    }

    public CopyTargetSpellEffect(boolean useController, boolean useLKI) {
        this(useController, useLKI, true);
    }

    public CopyTargetSpellEffect(boolean useController, boolean useLKI, boolean chooseTargets) {
        this(useController, useLKI, chooseTargets, 1);
    }

    public CopyTargetSpellEffect(boolean useController, boolean useLKI, boolean chooseTargets, int amount) {
        this(useController, useLKI, chooseTargets, amount, null);
    }

    /**
     * 
     * @param useController Whether to create the copy under the control of the original spell's controller (true) or the controller of the ability that this effect is on (false)
     * @param useLKI        Whether to get last-known information about the spell before resolving the effect (for instance for abilities which don't target a spell but reference it some other way)
     * @param chooseTargets Whether the new copy and choose new targets
     * @param amount        The amount of copies to create
     * @param applier       An applier to apply to the newly created copies. Used to change copiable values of the copy, such as types or name
     */
    public CopyTargetSpellEffect(boolean useController, boolean useLKI, boolean chooseTargets, int amount,
            StackObjectCopyApplier applier) {
        super(Outcome.Copy);
        this.useController = useController;
        this.useLKI = useLKI;
        this.chooseTargets = chooseTargets;
        this.amount = amount;
        this.applier = applier;
    }

    protected CopyTargetSpellEffect(final CopyTargetSpellEffect effect) {
        super(effect);
        this.useLKI = effect.useLKI;
        this.useController = effect.useController;
        this.copyThatSpellName = effect.copyThatSpellName;
        this.chooseTargets = effect.chooseTargets;
        this.amount = effect.amount;
        this.applier = effect.applier;
    }

    public Effect withSpellName(String copyThatSpellName) {
        this.copyThatSpellName = copyThatSpellName;
        return this;
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
            spell.createCopyOnStack(game, source, useController ? spell.getControllerId() : source.getControllerId(),
                    chooseTargets, amount, applier);
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
        return "copy " +
                getTargetPointer().describeTargets(mode.getTargets(), copyThatSpellName) +
                (chooseTargets ? ". You may choose new targets for the copy" : "");
    }
}
