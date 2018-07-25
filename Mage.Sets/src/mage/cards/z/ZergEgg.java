package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class ZergEgg extends CardImpl {

    public ZergEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At the beginning of your upkeep, you may sacrifice Zerg Egg. If you do, put two 1/1 green Zerg creature tokens with haste onto the battlefield.
    }

    public ZergEgg(final ZergEgg card) {
        super(card);
    }

    @Override
    public ZergEgg copy() {
        return new ZergEgg(this);
    }
}
