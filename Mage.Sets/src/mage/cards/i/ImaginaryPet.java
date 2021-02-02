
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

/**
 *
 * @author LoneFox
 */
public final class ImaginaryPet extends CardImpl {

    public ImaginaryPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you have a card in hand, return Imaginary Pet to its owner's hand.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandSourceEffect(true), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new CardsInHandCondition(ComparisonType.MORE_THAN, 0),
            "At the beginning of your upkeep, if you have a card in hand, return {this} to its owner's hand."));
    }

    private ImaginaryPet(final ImaginaryPet card) {
        super(card);
    }

    @Override
    public ImaginaryPet copy() {
        return new ImaginaryPet(this);
    }
}
