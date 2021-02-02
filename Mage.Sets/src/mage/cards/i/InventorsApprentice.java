
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author cg5
 */
public final class InventorsApprentice extends CardImpl {
    
    private static final String rule = "{this} gets +1/+1 as long as you control an artifact";
    
    public InventorsApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Inventor's Apprentice gets +1/+1 as long as you control an artifact.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent()), rule)));
    }

    private InventorsApprentice(final InventorsApprentice card) {
        super(card);
    }

    @Override
    public InventorsApprentice copy() {
        return new InventorsApprentice(this);
    }
}
