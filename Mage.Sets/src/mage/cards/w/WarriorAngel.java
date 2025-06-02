package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WarriorAngel extends CardImpl {

    public WarriorAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Warrior Angel deals damage, you gain that much life.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH)));
    }

    private WarriorAngel(final WarriorAngel card) {
        super(card);
    }

    @Override
    public WarriorAngel copy() {
        return new WarriorAngel(this);
    }
}
