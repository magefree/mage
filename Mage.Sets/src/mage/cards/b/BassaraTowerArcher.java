
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BassaraTowerArcher extends CardImpl {

    public BassaraTowerArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.HUMAN, SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private BassaraTowerArcher(final BassaraTowerArcher card) {
        super(card);
    }

    @Override
    public BassaraTowerArcher copy() {
        return new BassaraTowerArcher(this);
    }
}
