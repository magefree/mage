
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SultaiScavenger extends CardImpl {

    public SultaiScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Delve
        this.addAbility(new DelveAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SultaiScavenger(final SultaiScavenger card) {
        super(card);
    }

    @Override
    public SultaiScavenger copy() {
        return new SultaiScavenger(this);
    }
}
