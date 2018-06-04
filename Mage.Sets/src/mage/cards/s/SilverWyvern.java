
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetStackObject;
import mage.target.Targets;

/**
 *
 * @author LevelX2
 */
public final class SilverWyvern extends CardImpl {
    
    public SilverWyvern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {U}: Change the target of target spell or ability that targets only Silver Wyvern. The new target must be a creature.
        Effect effect = new ChooseNewTargetsTargetEffect(true, true);
        effect.setText("Change the target of target spell or ability that targets only {this}. The new target must be a creature");        
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{U}"));
        FilterStackObject filter = new FilterStackObject();
        filter.add(new TargetsOnlySourcePredicate(getId()));
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability);
        
    }

    public SilverWyvern(final SilverWyvern card) {
        super(card);
    }

    @Override
    public SilverWyvern copy() {
        return new SilverWyvern(this);
    }
}

class TargetsOnlySourcePredicate implements Predicate<MageObject> {

    private final UUID sourceId;

    public TargetsOnlySourcePredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        StackObject stackObject = game.getStack().getStackObject(input.getId());
        if (stackObject != null) {
            Targets spellTargets = stackObject.getStackAbility().getTargets();
            int numberOfTargets = 0;
            for (Target target : spellTargets) {
                if (target.getFirstTarget() == null || !target.getFirstTarget().toString().equals(sourceId.toString())) { // UUID != UUID does not work - it's always false
                    return false;
                }
                numberOfTargets += target.getTargets().size();
            }
            if (numberOfTargets == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "target spell or ability that targets only source";
    }
}
