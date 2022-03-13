
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetObject;
import mage.target.Targets;

/**
 *
 * @author spjspj
 */
public final class SirenStormtamer extends CardImpl {

    public SirenStormtamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {U}, Sacrifice Siren Stormtamer: Counter target spell or ability that targets you or a creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl("{U}"));
        ability.addTarget(new SirenStormtamerTargetObject());
        ability.addCost(new SacrificeSourceCost());

        this.addAbility(ability);
    }

    private SirenStormtamer(final SirenStormtamer card) {
        super(card);
    }

    @Override
    public SirenStormtamer copy() {
        return new SirenStormtamer(this);
    }
}

class SirenStormtamerTargetObject extends TargetObject {

    public SirenStormtamerTargetObject() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = "spell or ability that targets you or a creature you control";
    }

    public SirenStormtamerTargetObject(final SirenStormtamerTargetObject target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(id);
        return (stackObject instanceof Spell) || (stackObject instanceof StackAbility);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
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
                            if (targetedPermanent != null
                                    && targetedPermanent.isControlledBy(sourceControllerId)
                                    && targetedPermanent.isCreature(game)) {
                                return true;
                            }

                            if (sourceControllerId.equals(targetId)) {
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
    public Set<UUID> possibleTargets(UUID sourceControllerId,
                                     Ability source, Game game) {
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
                            if (targetedPermanent != null
                                    && targetedPermanent.isControlledBy(sourceControllerId)
                                    && targetedPermanent.isCreature(game)) {
                                possibleTargets.add(stackObject.getId());
                            }

                            if (sourceControllerId.equals(targetId)) {
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
    public SirenStormtamerTargetObject copy() {
        return new SirenStormtamerTargetObject(this);
    }

    @Override
    public Filter getFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
