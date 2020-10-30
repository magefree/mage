package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ShepherdOfHeroes extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(PartyCount.instance, 2);
    private static final String rule = "you gain 2 life for each creature in your party. " +
            "<i>(Your party consists of up to one each of Cleric, Rogue, Warrior, and Wizard.)</i>";

    public ShepherdOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Shepherd of Heroes enters the battlefield, you gain 2 life for each creature in your party.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainLifeEffect(xValue).setText("" + rule)
        ).addHint(PartyCountHint.instance));
    }

    private ShepherdOfHeroes(final ShepherdOfHeroes card) {
        super(card);
    }

    @Override
    public ShepherdOfHeroes copy() {
        return new ShepherdOfHeroes(this);
    }
}
