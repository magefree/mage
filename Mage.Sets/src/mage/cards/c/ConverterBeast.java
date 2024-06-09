package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConverterBeast extends CardImpl {

    public ConverterBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When Converter Beast enters the battlefield, incubate 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(5)));
    }

    private ConverterBeast(final ConverterBeast card) {
        super(card);
    }

    @Override
    public ConverterBeast copy() {
        return new ConverterBeast(this);
    }
}
