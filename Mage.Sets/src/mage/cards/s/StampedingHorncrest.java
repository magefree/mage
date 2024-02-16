
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class StampedingHorncrest extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Dinosaur");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public StampedingHorncrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Stampeding Horncrest has haste as long as you control another Dinosaur.
        Effect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(HasteAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter),
                "{this} has haste as long as you control another Dinosaur");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private StampedingHorncrest(final StampedingHorncrest card) {
        super(card);
    }

    @Override
    public StampedingHorncrest copy() {
        return new StampedingHorncrest(this);
    }
}
