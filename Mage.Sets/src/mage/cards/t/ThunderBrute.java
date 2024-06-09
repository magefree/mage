
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class ThunderBrute extends CardImpl {

    public ThunderBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Tribute 3</i>
        this.addAbility(new TributeAbility(3));
        // When Thunder Brute enters the battlefield, if tribute wasn't paid, it gains haste until end of turn.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TributeNotPaidCondition.instance,
                "When {this} enters the battlefield, if tribute wasn't paid, it gains haste until end of turn."));        
    }

    private ThunderBrute(final ThunderBrute card) {
        super(card);
    }

    @Override
    public ThunderBrute copy() {
        return new ThunderBrute(this);
    }
}
