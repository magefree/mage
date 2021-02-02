
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Quercitron
 */
public final class LongbowArcher extends CardImpl {

    public LongbowArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike; reach
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
    }

    private LongbowArcher(final LongbowArcher card) {
        super(card);
    }

    @Override
    public LongbowArcher copy() {
        return new LongbowArcher(this);
    }
}
