
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class HeadstrongBrute extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public HeadstrongBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Headstrong Brute can't block.
        this.addAbility(new CantBlockAbility());

        // Headstrong Brute has menace as long as you control another Pirate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(filter), "{this} has menace as long as you control another Pirate")));
    }

    private HeadstrongBrute(final HeadstrongBrute card) {
        super(card);
    }

    @Override
    public HeadstrongBrute copy() {
        return new HeadstrongBrute(this);
    }
}
