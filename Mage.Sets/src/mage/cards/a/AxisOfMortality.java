package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ExchangeLifeTwoTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxisOfMortality extends CardImpl {

    public AxisOfMortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // At the beginning of your upkeep, you may have two target players exchange life totals.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ExchangeLifeTwoTargetEffect(), TargetController.YOU, true
        );
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    private AxisOfMortality(final AxisOfMortality card) {
        super(card);
    }

    @Override
    public AxisOfMortality copy() {
        return new AxisOfMortality(this);
    }
}
