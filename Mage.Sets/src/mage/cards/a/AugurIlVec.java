
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class AugurIlVec extends CardImpl {

    public AugurIlVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // Sacrifice Augur il-Vec: You gain 4 life. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new GainLifeEffect(4), 
                new SacrificeSourceCost(), 
                new IsStepCondition(PhaseStep.UPKEEP)));
    }

    private AugurIlVec(final AugurIlVec card) {
        super(card);
    }

    @Override
    public AugurIlVec copy() {
        return new AugurIlVec(this);
    }
}
