
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class WeaponsTrainer extends CardImpl {

    private static final String rule = "Other creatures you control get +1/+0 as long as you control an Equipment.";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an Equipment");

    static {
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public WeaponsTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Other creatures you control get +1/+0 as long as you control an Equipment.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, true),
                new PermanentsOnTheBattlefieldCondition(filter), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public WeaponsTrainer(final WeaponsTrainer card) {
        super(card);
    }

    @Override
    public WeaponsTrainer copy() {
        return new WeaponsTrainer(this);
    }
}
