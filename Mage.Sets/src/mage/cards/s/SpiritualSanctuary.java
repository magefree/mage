
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 *
 * @author TheElk801
 */
public final class SpiritualSanctuary extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public SpiritualSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of each player's upkeep, if that player controls a Plains, they gain 1 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new GainLifeTargetEffect(1).setText("they gain 1 life"),
                        TargetController.ANY, false
                ),
                new PermanentsOnTheBattlefieldCondition(filter),
                "at the beginning of each player's upkeep, "
                + "if that player controls a Plains, they gain 1 life"
        ));
    }

    private SpiritualSanctuary(final SpiritualSanctuary card) {
        super(card);
    }

    @Override
    public SpiritualSanctuary copy() {
        return new SpiritualSanctuary(this);
    }
}
