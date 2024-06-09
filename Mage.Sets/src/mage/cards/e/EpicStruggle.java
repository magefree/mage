
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EpicStruggle extends CardImpl {

    public EpicStruggle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // At the beginning of your upkeep, if you control twenty or more creatures, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_CREATURE, ComparisonType.MORE_THAN, 19),
                "At the beginning of your upkeep, if you control twenty or more creatures, you win the game."
        ).addHint(new ValueHint("Creatures you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE))));
    }

    private EpicStruggle(final EpicStruggle card) {
        super(card);
    }

    @Override
    public EpicStruggle copy() {
        return new EpicStruggle(this);
    }
}
