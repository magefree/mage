/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 * @author LevelX2
 */
public class ImproviseAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final FilterArtifactPermanent filterUntapped = new FilterArtifactPermanent();

    static {
        filterUntapped.add(Predicates.not(TappedPredicate.instance));
    }

    public ImproviseAbility() {
        super(Zone.STACK, null);
        this.setRuleAtTheTop(true);
    }

    public ImproviseAbility(final ImproviseAbility ability) {
        super(ability);
    }

    @Override
    public ImproviseAbility copy() {
        return new ImproviseAbility(this);
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && game.getBattlefield().contains(filterUntapped, controller.getId(), 1, game)) {
            if (source.getAbilityType() == AbilityType.SPELL && unpaid.getMana().getGeneric() > 0) {
                SpecialAction specialAction = new ImproviseSpecialAction(unpaid);
                specialAction.setControllerId(source.getControllerId());
                specialAction.setSourceId(source.getSourceId());
                // create filter for possible artifacts to tap
                FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent();
                filter.add(Predicates.not(TappedPredicate.instance));
                Target target = new TargetControlledPermanent(1, unpaid.getMana().getGeneric(), filter, true);
                target.setTargetName("artifact to Improvise");
                specialAction.addTarget(target);
                if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                    game.getState().getSpecialActions().add(specialAction);
                }
            }
        }
    }

    @Override
    public String getRule() {
        return "Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)</i>";
    }
}

class ImproviseSpecialAction extends SpecialAction {

    public ImproviseSpecialAction(ManaCost unpaid) {
        super(Zone.ALL, true);
        setRuleVisible(false);
        this.addEffect(new ImproviseEffect(unpaid));
    }

    public ImproviseSpecialAction(final ImproviseSpecialAction ability) {
        super(ability);
    }

    @Override
    public ImproviseSpecialAction copy() {
        return new ImproviseSpecialAction(this);
    }
}

class ImproviseEffect extends OneShotEffect {

    private final ManaCost unpaid;

    public ImproviseEffect(ManaCost unpaid) {
        super(Outcome.Benefit);
        this.unpaid = unpaid;
        this.staticText = "Improvise (Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)";
    }

    public ImproviseEffect(final ImproviseEffect effect) {
        super(effect);
        this.unpaid = effect.unpaid;
    }

    @Override
    public ImproviseEffect copy() {
        return new ImproviseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (controller != null && spell != null) {
            for (UUID artifactId : this.getTargetPointer().getTargets(game, source)) {
                Permanent perm = game.getPermanent(artifactId);
                if (perm == null) {
                    continue;
                }
                if (!perm.isTapped() && perm.tap(game)) {
                    ManaPool manaPool = controller.getManaPool();
                    manaPool.addMana(Mana.ColorlessMana(1), game, source);
                    manaPool.unlockManaType(ManaType.COLORLESS);
                    if (!game.isSimulation()) {
                        game.informPlayers("Improvise: " + controller.getLogName() + " taps " + perm.getLogName() + " to pay {1}");
                    }
                    spell.setDoneActivatingManaAbilities(true);
                }

            }
            return true;
        }
        return false;
    }

}
