
package mage.cards.s;

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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetStackObject;
import mage.target.Targets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
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
        Ability ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addTarget(new SirenStormtamerTarget());
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

class SirenStormtamerTarget extends TargetStackObject {

    public SirenStormtamerTarget() {
        super();
        withTargetName("spell or ability that targets you or a creature you control");
    }

    private SirenStormtamerTarget(final SirenStormtamerTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        Set<UUID> targetsMe = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            Targets objectTargets = stackObject.getStackAbility().getTargets();
            for (Target target : objectTargets) {
                for (UUID targetId : target.getTargets()) {
                    Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                    if (targetedPermanent != null
                            && targetedPermanent.isControlledBy(sourceControllerId)
                            && targetedPermanent.isCreature(game)) {
                        targetsMe.add(stackObject.getId());
                    }
                    if (sourceControllerId.equals(targetId)) {
                        targetsMe.add(stackObject.getId());
                    }
                }
            }
        }
        possibleTargets.removeIf(id -> !targetsMe.contains(id));

        return possibleTargets;
    }

    @Override
    public SirenStormtamerTarget copy() {
        return new SirenStormtamerTarget(this);
    }
}
