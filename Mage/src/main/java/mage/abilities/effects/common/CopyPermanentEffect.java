
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.ApplyToPermanent;
import mage.util.functions.EmptyApplyToPermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CopyPermanentEffect extends OneShotEffect {

    private FilterPermanent filter;
    private ApplyToPermanent applier;
    private Permanent bluePrintPermanent;
    private boolean useTargetOfAbility;

    public CopyPermanentEffect() {
        this(new FilterCreaturePermanent());
    }

    public CopyPermanentEffect(ApplyToPermanent applier) {
        this(StaticFilters.FILTER_PERMANENT_CREATURE, applier);
    }

    public CopyPermanentEffect(FilterPermanent filter) {
        this(filter, new EmptyApplyToPermanent());
    }

    public CopyPermanentEffect(FilterPermanent filter, ApplyToPermanent applier) {
        this(filter, applier, false);
    }

    public CopyPermanentEffect(FilterPermanent filter, ApplyToPermanent applier, boolean useTarget) {
        super(Outcome.Copy);
        this.applier = applier;
        this.filter = filter;
        this.useTargetOfAbility = useTarget;
        this.staticText = "as a copy of any " + filter.getMessage() + " on the battlefield";
    }

    public CopyPermanentEffect(final CopyPermanentEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.applier = effect.applier;
        if (effect.bluePrintPermanent != null) {
            this.bluePrintPermanent = effect.bluePrintPermanent.copy();
        }
        this.useTargetOfAbility = effect.useTargetOfAbility;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = game.getObject(source.getSourceId());
        }
        if (controller != null && sourcePermanent != null) {
            Permanent copyFromPermanent = null;
            if (useTargetOfAbility) {
                copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            } else {
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                    controller.choose(Outcome.Copy, target, source.getSourceId(), game);
                    copyFromPermanent = game.getPermanent(target.getFirstTarget());
                }
            }
            if (copyFromPermanent != null) {
                bluePrintPermanent = game.copyPermanent(copyFromPermanent, sourcePermanent.getId(), source, applier);
                if (bluePrintPermanent == null) {
                    return false;
                }

                // if object is a copy of an aura, it needs to attach again for new target
                if (bluePrintPermanent.hasSubtype(SubType.AURA, game)) {
                    //copied from mage.cards.c.CopyEnchantment.java

                    // permanent can be attached (Estrid's Mask) or enchant (Utopia Sprawl)
                    // TODO: fix Animate Dead -- it's can't be copied (can't retarget)
                    Outcome auraOutcome = Outcome.BoostCreature;
                    Target auraTarget = null;

                    // attach - search effect in spell ability (example: cast Utopia Sprawl, cast Estrid's Invocation on it)
                    for (Ability ability : bluePrintPermanent.getAbilities()) {
                        if (ability instanceof SpellAbility) {
                            for (Effect effect : ability.getEffects()) {
                                if (effect instanceof AttachEffect) {
                                    if (bluePrintPermanent.getSpellAbility().getTargets().size() > 0) {
                                        auraTarget = bluePrintPermanent.getSpellAbility().getTargets().get(0);
                                        auraOutcome = effect.getOutcome();
                                    }
                                }
                            }
                        }
                    }

                    // enchant - search in all abilities (example: cast Estrid's Invocation on enchanted creature by Estrid, the Masked second ability, cast Estrid's Invocation on it)
                    if (auraTarget == null) {
                        for (Ability ability : bluePrintPermanent.getAbilities()) {
                            if (ability instanceof EnchantAbility) {
                                if (ability.getTargets().size() > 0) { // Animate Dead don't have targets
                                    auraTarget = ability.getTargets().get(0);
                                    for (Effect effect : ability.getEffects()) {
                                        // first outcome
                                        auraOutcome = effect.getOutcome();
                                    }
                                }
                            }
                        }
                    }

                    /* if this is a copy of a copy, the copy's target has been
                     * copied and needs to be cleared
                     */
                    if (auraTarget != null) {
                        // clear selected target
                        if (auraTarget.getFirstTarget() != null) {
                            auraTarget.remove(auraTarget.getFirstTarget());
                        }

                        // select new target
                        auraTarget.setNotTarget(true);
                        if (controller.choose(auraOutcome, auraTarget, source.getSourceId(), game)) {
                            UUID targetId = auraTarget.getFirstTarget();
                            Permanent targetPermanent = game.getPermanent(targetId);
                            Player targetPlayer = game.getPlayer(targetId);
                            if (targetPermanent != null) {
                                targetPermanent.addAttachment(sourcePermanent.getId(), game);
                            } else if (targetPlayer != null) {
                                targetPlayer.addAttachment(sourcePermanent.getId(), game);
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Permanent getBluePrintPermanent() {
        return bluePrintPermanent;
    }

    @Override
    public CopyPermanentEffect copy() {
        return new CopyPermanentEffect(this);
    }
}
