
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
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

/**
 *
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
        this.bluePrintPermanent = effect.bluePrintPermanent;
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
                
                //if object is a copy of an aura, it needs to attach
                if (bluePrintPermanent.hasSubtype(SubType.AURA, game)){
                    //copied from mage.cards.c.CopyEnchantment.java
                    Target target = bluePrintPermanent.getSpellAbility().getTargets().get(0);
                    Outcome auraOutcome = Outcome.BoostCreature;
                    for (Ability ability : bluePrintPermanent.getAbilities()) {
                        if (ability instanceof SpellAbility) {
                            for (Effect effect : ability.getEffects()) {
                                if (effect instanceof AttachEffect) {
                                    auraOutcome = effect.getOutcome();
                                }
                            }
                        }
                    }
                    
                    /*if this is a copy of a copy, the copy's target has been
                     *copied and needs to be cleared
                     */
                    {
                        UUID targetId = target.getFirstTarget();
                        if(targetId != null)
                            target.remove(targetId);
                    }
                    
                    target.setNotTarget(true);
                    if (controller.choose(auraOutcome, target, source.getSourceId(), game)) {
                        UUID targetId = target.getFirstTarget();
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
