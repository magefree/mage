package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

public final class ManticoreEternal extends CardImpl {

    public ManticoreEternal(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.MANTICORE);
        power = new MageInt(5);
        toughness = new MageInt(4);

        // Afflict 3
        addAbility(new AfflictAbility(3));

        // Manticore Eternal attacks each combat if able
        addAbility(new AttacksEachCombatStaticAbility());
    }

    private ManticoreEternal(final ManticoreEternal manticoreEternal) {
        super(manticoreEternal);
    }

    public ManticoreEternal copy() {
        return new ManticoreEternal(this);
    }
}
