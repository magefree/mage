package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.util.functions.StackObjectCopyApplier;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CopyTargetStackObjectEffect extends OneShotEffect {

    private final boolean useController;
    private final boolean useLKI;
    private String objectName = "that spell";
    private final boolean chooseTargets;
    private final int amount;
    private final StackObjectCopyApplier applier;

    public CopyTargetStackObjectEffect() {
        this(false);
    }

    public CopyTargetStackObjectEffect(boolean useLKI) {
        this(false, useLKI, true);
    }

    public CopyTargetStackObjectEffect(boolean useController, boolean useLKI, boolean chooseTargets) {
        this(useController, useLKI, chooseTargets, 1, null);
    }

    /**
     * 
     * @param useController Whether to create the copy under the control of the original spell's controller (true) or the controller of the ability that this effect is on (false)
     * @param useLKI        Whether to get last-known information about the spell before resolving the effect (for instance for abilities which don't target a spell but reference it some other way)
     * @param chooseTargets Whether the new copy and choose new targets
     * @param amount        The amount of copies to create
     * @param applier       An applier to apply to the newly created copies. Used to change copiable values of the copy, such as types or name
     */
    public CopyTargetStackObjectEffect(boolean useController, boolean useLKI, boolean chooseTargets, int amount,
                                       StackObjectCopyApplier applier) {
        super(Outcome.Copy);
        this.useController = useController;
        this.useLKI = useLKI;
        this.chooseTargets = chooseTargets;
        this.amount = amount;
        this.applier = applier;
    }

    protected CopyTargetStackObjectEffect(final CopyTargetStackObjectEffect effect) {
        super(effect);
        this.useLKI = effect.useLKI;
        this.useController = effect.useController;
        this.objectName = effect.objectName;
        this.chooseTargets = effect.chooseTargets;
        this.amount = effect.amount;
        this.applier = effect.applier;
    }

    public CopyTargetStackObjectEffect withText(String objectName) {
        this.objectName = objectName;
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject;
        if (useLKI) {
            stackObject = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        } else {
            stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        }
        if (stackObject == null) {
            stackObject = (StackObject) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK);
        }
        if (stackObject != null) {
            stackObject.createCopyOnStack(game, source,
                    useController ? stackObject.getControllerId() : source.getControllerId(),
                    chooseTargets, amount, applier);
            return true;
        }
        return false;
    }

    @Override
    public CopyTargetStackObjectEffect copy() {
        return new CopyTargetStackObjectEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "copy " +
                getTargetPointer().describeTargets(mode.getTargets(), objectName) +
                (chooseTargets ? ". You may choose new targets for the copy" : "");
    }
}
