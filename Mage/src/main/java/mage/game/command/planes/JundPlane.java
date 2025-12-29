package mage.game.command.planes;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.keyword.DevourAbility;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author muz
 */
public class JundPlane extends Plane {

    private static final FilterSpell filter = new FilterSpell("a creature spell that’s black, red, or green");
    private static final Predicate<MageObject> predicate = Predicates.or(
        new ColorPredicate(ObjectColor.BLACK),
        new ColorPredicate(ObjectColor.RED),
        new ColorPredicate(ObjectColor.GREEN)
    );

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(predicate);
    }


    public JundPlane() {
        this.setPlaneType(Planes.PLANE_JUND);

        // Whenever a player casts a creature spell that’s black, red, or green, it gains devour 5.
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, new JundPlaneGainDevourEffect(), filter, false, SetTargetPointer.SPELL);
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, create two 1/1 red Goblin creature tokens
        Effect chaosEffect = new CreateTokenEffect(new GoblinToken(), 2);
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect);

        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }

    private JundPlane(final JundPlane plane) {
        super(plane);
    }

    @Override
    public JundPlane copy() {
        return new JundPlane(this);
    }
}

class JundPlaneGainDevourEffect extends ContinuousEffectImpl {

    private Ability ability = new DevourAbility(5);
    private int zoneChangeCounter;
    private UUID permanentId;

    JundPlaneGainDevourEffect() {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains devour 5";
    }

    private JundPlaneGainDevourEffect(final JundPlaneGainDevourEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.permanentId = effect.permanentId;
    }

    @Override
    public JundPlaneGainDevourEffect copy() {
        return new JundPlaneGainDevourEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (object != null) {
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
            permanentId = object.getSourceId();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null && permanent.getZoneChangeCounter(game) <= zoneChangeCounter) {
            permanent.addAbility(ability, source.getSourceId(), game);
        } else {
            if (game.getState().getZoneChangeCounter(permanentId) >= zoneChangeCounter) {
                discard();
            }
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) { // Devour checked while spell is on the stack so needed to give it already to the spell
                game.getState().addOtherAbility(spell.getCard(), ability, true);
            }
        }
        return true;
    }

}