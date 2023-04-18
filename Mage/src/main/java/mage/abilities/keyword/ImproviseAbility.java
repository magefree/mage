package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ManaOptions;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * 702.125. Improvise
 * <p>
 * 702.125a Improvise is a static ability that functions while the spell with improvise is on the stack. “Improvise”
 * means “For each generic mana in this spell’s total cost, you may tap an untapped artifact you control rather
 * than pay that mana.”
 * <p>
 * 702.125b The improvise ability isn’t an additional or alternative cost and applies only after the total cost of
 * the spell with improvise is determined.
 * <p>
 * 702.125c Multiple instances of improvise on the same spell are redundant.
 *
 * @author LevelX2, JayDi85
 */
public class ImproviseAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final FilterControlledArtifactPermanent filterUntapped = new FilterControlledArtifactPermanent("untapped artifact you control");

    static {
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    private static final DynamicValue untappedCount = new PermanentsOnBattlefieldCount(filterUntapped);

    public ImproviseAbility() {
        super(Zone.ALL, null);
        this.setRuleAtTheTop(true);
        this.addHint(new ValueHint("Untapped artifacts you control", untappedCount));
    }

    public ImproviseAbility(final ImproviseAbility ability) {
        super(ability);
    }

    @Override
    public ImproviseAbility copy() {
        return new ImproviseAbility(this);
    }


    @Override
    public String getRule() {
        return "improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)</i>";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.AFTER;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        int canPayCount = untappedCount.calculate(game, source, null);
        if (controller != null && canPayCount > 0) {
            if (source.getAbilityType() == AbilityType.SPELL && unpaid.getMana().getGeneric() > 0) {
                SpecialAction specialAction = new ImproviseSpecialAction(unpaid, this);
                specialAction.setControllerId(source.getControllerId());
                specialAction.setSourceId(source.getSourceId());
                // create filter for possible artifacts to tap
                Target target = new TargetControlledPermanent(1, unpaid.getMana().getGeneric(), filterUntapped, true);
                target.setTargetName("artifact to tap as Improvise's pay");
                specialAction.addTarget(target);
                if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                    game.getState().getSpecialActions().add(specialAction);
                }
            }
        }
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions options = new ManaOptions();
        Player controller = game.getPlayer(source.getControllerId());
        int canPayCount = untappedCount.calculate(game, source, null);
        if (controller != null && canPayCount > 0) {
            options.addMana(Mana.GenericMana(Math.min(unpaid.getMana().getGeneric(), canPayCount)));
        }
        return options;
    }
}

class ImproviseSpecialAction extends SpecialAction {

    public ImproviseSpecialAction(ManaCost unpaid, AlternateManaPaymentAbility manaAbility) {
        super(Zone.ALL, manaAbility);
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
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
        if (controller == null || spell == null) {
            return false;
        }
        for (UUID artifactId : this.getTargetPointer().getTargets(game, source)) {
            Permanent perm = game.getPermanent(artifactId);
            if (perm == null) {
                continue;
            }
            if (!perm.isTapped() && perm.tap(source, game)) {
                ManaPool manaPool = controller.getManaPool();
                manaPool.addMana(Mana.ColorlessMana(1), game, source);
                manaPool.unlockManaType(ManaType.COLORLESS);
                if (!game.isSimulation()) {
                    game.informPlayers("Improvise: " + controller.getLogName() + " taps " + perm.getLogName() + " to pay {1}");
                }

                // can't use mana abilities after that (improvise cost must be payed after mana abilities only)
                spell.setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.AFTER);
            }

        }
        return true;
    }

}
