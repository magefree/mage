
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GoblinSpelunkers extends CardImpl {

    public GoblinSpelunkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new MountainwalkAbility());
    }

    private GoblinSpelunkers(final GoblinSpelunkers card) {
        super(card);
    }

    @Override
    public GoblinSpelunkers copy() {
        return new GoblinSpelunkers(this);
    }
}
