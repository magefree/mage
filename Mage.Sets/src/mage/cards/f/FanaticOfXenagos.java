
package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FanaticOfXenagos extends CardImpl {

    public FanaticOfXenagos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Tribute 1
        this.addAbility(new TributeAbility(1));
        // When Fanatic of Xenagos enters the battlefield, if tribute wasn't paid, it gets +1/+1 and gains haste until end of turn.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn));
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TributeNotPaidCondition.instance,
                "When {this} enters the battlefield, if tribute wasn't paid, it gets +1/+1 and gains haste until end of turn."));
    }

    private FanaticOfXenagos(final FanaticOfXenagos card) {
        super(card);
    }

    @Override
    public FanaticOfXenagos copy() {
        return new FanaticOfXenagos(this);
    }
}
