package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class ManticoreElemental extends CardImpl {

    public ManticoreElemental(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        subtype.add("Zombie");
        subtype.add("Manticore");
        power = new MageInt(5);
        toughness = new MageInt(4);

        // Afflict 3
        addAbility(new AfflictAbility(3));

        // Manticore Elemental attacks each combat if able
        addAbility(new AttacksEachCombatStaticAbility());
    }

    public ManticoreElemental(final ManticoreElemental manticoreElemental) {
        super(manticoreElemental);
    }

    public ManticoreElemental copy() {
        return new ManticoreElemental(this);
    }
}
