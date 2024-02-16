package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TangleweaveArmor extends CardImpl {

    public TangleweaveArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}{G}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +X/+X, where X is the greatest mana value among your commanders
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new BoostEquippedEffect(TangleweaveArmorDynamicValue.instance, TangleweaveArmorDynamicValue.instance)
        ));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), false));
    }

    private TangleweaveArmor(final TangleweaveArmor card) {
        super(card);
    }

    @Override
    public TangleweaveArmor copy() {
        return new TangleweaveArmor(this);
    }
}

enum TangleweaveArmorDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getCommanderCardsFromAnyZones(
                game.getPlayer(sourceAbility.getControllerId()), CommanderCardType.ANY, Zone.ALL)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public TangleweaveArmorDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the greatest mana value among your commanders";
    }
}
