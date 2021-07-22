
package mage.cards.c;

import mage.MageInt;
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
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoffinPuppets extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("you control a Swamp");
    private static final FilterControlledPermanent filter2
            = new FilterControlledLandPermanent("lands");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    private static final Condition condition = new CompoundCondition(
            "during your upkeep and only if you control a Swamp",
            new PermanentsOnTheBattlefieldCondition(filter),
            new IsStepCondition(PhaseStep.UPKEEP)
    );

    public CoffinPuppets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sacrifice two lands: Return Coffin Puppets from your graveyard to the battlefield. Activate this ability only during your upkeep and only if you control a Swamp.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new SacrificeTargetCost(
                        new TargetControlledPermanent(2, 2, filter2, true)
                ), condition
        ));
    }

    private CoffinPuppets(final CoffinPuppets card) {
        super(card);
    }

    @Override
    public CoffinPuppets copy() {
        return new CoffinPuppets(this);
    }
}
