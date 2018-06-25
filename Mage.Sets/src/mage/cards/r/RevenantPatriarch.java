
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SkipNextCombatEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.target.TargetPlayer;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author ilcartographer
 */
public final class RevenantPatriarch extends CardImpl {

    public RevenantPatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Revenant Patriarch enters the battlefield, if {W} was spent to cast it, target player skips their next combat phase.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new SkipNextCombatEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new ManaWasSpentCondition(ColoredManaSymbol.W),
                "if {W} was spent to cast it, target player skips their next combat phase."), new ManaSpentToCastWatcher());
        // Revenant Patriarch can't block.
        this.addAbility(new CantBlockAbility());
    }

    public RevenantPatriarch(final RevenantPatriarch card) {
        super(card);
    }

    @Override
    public RevenantPatriarch copy() {
        return new RevenantPatriarch(this);
    }
}
