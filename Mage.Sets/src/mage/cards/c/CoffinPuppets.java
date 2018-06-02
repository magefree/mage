
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class CoffinPuppets extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control a Swamp");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public CoffinPuppets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sacrifice two lands: Return Coffin Puppets from your graveyard to the battlefield. Activate this ability only during your upkeep and only if you control a Swamp.
        Condition condition = new CompoundCondition("during your upkeep and only if you control a Swamp",new PermanentsOnTheBattlefieldCondition(filter), new IsStepCondition(PhaseStep.UPKEEP));
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("two lands"), true)),
                condition);
        this.addAbility(ability);
    }

    public CoffinPuppets(final CoffinPuppets card) {
        super(card);
    }

    @Override
    public CoffinPuppets copy() {
        return new CoffinPuppets(this);
    }
}
