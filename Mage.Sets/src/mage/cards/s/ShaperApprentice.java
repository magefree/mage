
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class ShaperApprentice extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Merfolk");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ShaperApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shaper Apprentice has flying as long as you control another Merfolk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(filter), "{this} has flying as long as you control another Merfolk")));
    }

    private ShaperApprentice(final ShaperApprentice card) {
        super(card);
    }

    @Override
    public ShaperApprentice copy() {
        return new ShaperApprentice(this);
    }
}
