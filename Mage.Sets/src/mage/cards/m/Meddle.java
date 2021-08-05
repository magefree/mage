package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Meddle extends CardImpl {

    public Meddle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // If target spell has only one target and that target is a creature, change that spell's target to another creature.
        this.getSpellAbility().addEffect(new MeddleEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Meddle(final Meddle card) {
        super(card);
    }

    @Override
    public Meddle copy() {
        return new Meddle(this);
    }
}

class MeddleEffect extends OneShotEffect {

    MeddleEffect() {
        super(Outcome.Benefit);
        staticText = "If target spell has only one target and that target is a creature, " +
                "change that spell's target to another creature.";
    }

    private MeddleEffect(final MeddleEffect effect) {
        super(effect);
    }

    @Override
    public MeddleEffect copy() {
        return new MeddleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!checkTarget(game, source)) {
            return false;
        }
        Spell spell = game.getSpell(source.getFirstTarget());
        spell.chooseNewTargets(game, source.getControllerId(), true, false, null);
        return true;
    }

    private static final boolean checkTarget(Game game, Ability source) {
        StackObject stackObject = game.getState().getStack().getStackObject(source.getFirstTarget());
        if (stackObject == null) {
            return false;
        }
        int numberOfTargets = 0;
        for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
            Mode mode = stackObject.getStackAbility().getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    if (numberOfTargets++ > 1) {
                        return false;
                    }
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null || !permanent.isCreature(game)) {
                        return false;
                    }
                }
            }
        }
        return numberOfTargets == 1;
    }
}
