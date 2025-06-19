package mage.cards.s;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritualSanctuary extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PLAINS, "that player controls a Plains");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, false);

    public SpiritualSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of each player's upkeep, if that player controls a Plains, they gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new GainLifeTargetEffect(1).setText("they gain 1 life"), false
        ).withInterveningIf(condition));
    }

    private SpiritualSanctuary(final SpiritualSanctuary card) {
        super(card);
    }

    @Override
    public SpiritualSanctuary copy() {
        return new SpiritualSanctuary(this);
    }
}
