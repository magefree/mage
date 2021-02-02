
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RustrazorButcher extends CardImpl {

    public RustrazorButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(WitherAbility.getInstance());
    }

    private RustrazorButcher(final RustrazorButcher card) {
        super(card);
    }

    @Override
    public RustrazorButcher copy() {
        return new RustrazorButcher(this);
    }
}
