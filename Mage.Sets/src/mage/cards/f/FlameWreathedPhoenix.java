
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
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
public final class FlameWreathedPhoenix extends CardImpl {

    public FlameWreathedPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.PHOENIX);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Tribute 2 (As this creature enters the battlefield, an opponent of your choice may put 2 +1/+1 counter on it.)
        this.addAbility(new TributeAbility(2));
        // When Flame-Wreathed Phoenix enters the battlefield, if tribute wasn't paid, it gains haste and "When this creature dies, return it to its owner's hand."
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield));
        Effect effect = new GainAbilitySourceEffect(new DiesSourceTriggeredAbility(new ReturnToHandSourceEffect()));
        ability.addEffect(effect);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TributeNotPaidCondition.instance,
                "When {this} enters the battlefield, if tribute wasn't paid, it gains haste and \"When this creature dies, return it to its owner's hand.\""));

    }

    private FlameWreathedPhoenix(final FlameWreathedPhoenix card) {
        super(card);
    }

    @Override
    public FlameWreathedPhoenix copy() {
        return new FlameWreathedPhoenix(this);
    }
}
