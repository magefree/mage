package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.common.PlanarRollWatcher;

/**
 * @author spjspj
 */
public class FeedingGroundsPlane extends Plane {

    private static final String rule = "put X +1/+1 counters on target creature, where X is that creature's mana value";

    public FeedingGroundsPlane() {
        this.setPlaneType(Planes.PLANE_FEEDING_GROUNDS);
        this.setExpansionSetCodeForImage("PCA");

        // Red spells cost {1} less to cast.  Green spells cost {1} less to cast
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new FeedingGroundsEffect());
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, target red or green creature gets X +1/+1 counters
        Effect chaosEffect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(), TargetManaValue.instance);
        Target chaosTarget = new TargetCreaturePermanent(1, 1, StaticFilters.FILTER_PERMANENT_A_CREATURE, false);

        List<Effect> chaosEffects = new ArrayList<>();
        chaosEffects.add(chaosEffect);
        List<Target> chaosTargets = new ArrayList<>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}

class FeedingGroundsEffect extends CostModificationEffectImpl {

    private static final FilterCard filter = new FilterCard("Red spells or Green spells");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN)));
    }

    private static final String rule = "Red spells cost {1} less to cast. Green spells cost {1} less to cast";
    private int amount = 1;

    public FeedingGroundsEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = 1;
        this.staticText = rule;
    }

    protected FeedingGroundsEffect(FeedingGroundsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        MageObject object = abilityToModify.getSourceObject(game);
        int reduce = 0;
        if (object != null) {
            if (object.getColor(game).isRed()) {
                reduce++;
            }
            if (object.getColor(game).isGreen()) {
                reduce++;
            }
        }
        CardUtil.reduceCost(abilityToModify, reduce);
        return true;
    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param card
     * @param source
     * @param game
     * @return
     */
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Plane cPlane = game.getState().getCurrentPlane();
            if (cPlane == null) {
                return false;
            }
            if (!cPlane.getPlaneType().equals(Planes.PLANE_FEEDING_GROUNDS)) {
                return false;
            }

            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                return filter.match(spell, game) && selectedByRuntimeData(spell, source, game);
            } else {
                // used at least for flashback ability because Flashback ability doesn't use stack
                Card sourceCard = game.getCard(abilityToModify.getSourceId());
                return filter.match(sourceCard, game) && selectedByRuntimeData(sourceCard, source, game);
            }
        }
        return false;
    }

    @Override
    public FeedingGroundsEffect copy() {
        return new FeedingGroundsEffect(this);
    }
}
