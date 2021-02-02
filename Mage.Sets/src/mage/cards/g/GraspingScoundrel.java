
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Will
 */
public final class GraspingScoundrel extends CardImpl{
    public GraspingScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Grasping Scoundrel gets +1/+0 as long as it's attacking.
        this.addAbility(
            new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                    SourceAttackingCondition.instance,
                    "{this} gets +1/+0 as long as it's attacking"
        )));
    }

    private GraspingScoundrel(final GraspingScoundrel card) {
        super(card);
    }

    @Override
    public GraspingScoundrel copy() {
        return new GraspingScoundrel(this);
    }
}
