package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class ManticoreEternal extends CardImpl {

    public ManticoreEternal(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        subtype.add("Zombie");
        subtype.add("Manticore");
        power = new MageInt(5);
        toughness = new MageInt(4);

        // Afflict 3
        addAbility(new AfflictAbility(3));

        // Manticore Eternal attacks each combat if able
        addAbility(new AttacksEachCombatStaticAbility());
    }

    public ManticoreEternal(final ManticoreEternal manticoreEternal) {
        super(manticoreEternal);
    }

    public ManticoreEternal copy() {
        return new ManticoreEternal(this);
    }
}
