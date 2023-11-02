package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GregorShrewdMagistrate extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public GregorShrewdMagistrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Whenever Glenn deals combat damage to a player, draw cards equal to his power.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(xValue).setText("draw cards equal to its power"), false
        ));
    }

    private GregorShrewdMagistrate(final GregorShrewdMagistrate card) {
        super(card);
    }

    @Override
    public GregorShrewdMagistrate copy() {
        return new GregorShrewdMagistrate(this);
    }
}
