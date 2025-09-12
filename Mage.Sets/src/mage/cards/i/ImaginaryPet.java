package mage.cards.i;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ImaginaryPet extends CardImpl {

    private static final Condition condition = new InvertCondition(HellbentCondition.instance, "you have a card in hand");

    public ImaginaryPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you have a card in hand, return Imaginary Pet to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ReturnToHandSourceEffect(true)).withInterveningIf(condition));
    }

    private ImaginaryPet(final ImaginaryPet card) {
        super(card);
    }

    @Override
    public ImaginaryPet copy() {
        return new ImaginaryPet(this);
    }
}
