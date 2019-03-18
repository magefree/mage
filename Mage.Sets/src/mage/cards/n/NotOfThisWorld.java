
package mage.cards.n;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetObject;
import mage.target.Targets;

/**
 * @author Rafbill
 */
public final class NotOfThisWorld extends CardImpl {

    public NotOfThisWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{7}");
        this.subtype.add(SubType.ELDRAZI);

        // Counter target spell or ability that targets a permanent you control.
        this.getSpellAbility().addTarget(
                new TargetStackObjectTargetingControlledPermanent());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        // Not of This World costs {7} less to cast if it targets a spell or ability that targets a creature you control with power 7 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(7, NotOfThisWorldCondition.instance)));
    }

    public NotOfThisWorld(final NotOfThisWorld card) {
        super(card);
    }

    @Override
    public NotOfThisWorld copy() {
        return new NotOfThisWorld(this);
    }
}

class TargetStackObjectTargetingControlledPermanent extends TargetObject {

    public TargetStackObjectTargetingControlledPermanent() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = "spell or ability that targets a permanent you control";
    }

    public TargetStackObjectTargetingControlledPermanent(final TargetStackObjectTargetingControlledPermanent target) {
        super(target);
    }

    @Override
    public Filter getFilter() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(id);
        return (stackObject instanceof Spell) || (stackObject instanceof StackAbility);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if ((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) {
                Targets objectTargets = stackObject.getStackAbility().getTargets();
                if (!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent != null && targetedPermanent.isControlledBy(sourceControllerId)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId,
                                     Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if ((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) {
                Targets objectTargets = stackObject.getStackAbility().getTargets();
                if (!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent != null && targetedPermanent.isControlledBy(sourceControllerId)) {
                                possibleTargets.add(stackObject.getId());
                            }
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetStackObjectTargetingControlledPermanent copy() {
        return new TargetStackObjectTargetingControlledPermanent(this);
    }

}

enum NotOfThisWorldCondition implements Condition {
    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control with power 7 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }



    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell != null && sourceSpell.getStackAbility().getTargets().isChosen()) {
            StackObject objectToCounter = game.getStack().getStackObject(sourceSpell.getStackAbility().getTargets().getFirstTarget());
            if (objectToCounter != null) {
                for (Target target : objectToCounter.getStackAbility().getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                        if (targetedPermanent != null && filter.match(targetedPermanent, sourceSpell.getSourceId(), sourceSpell.getControllerId(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a spell or ability that targets a creature you control with power 7 or greater";
    }

}
