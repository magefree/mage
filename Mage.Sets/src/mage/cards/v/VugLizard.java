

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class VugLizard extends CardImpl {

    public VugLizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

    this.addAbility(new MountainwalkAbility());
    this.addAbility(new EchoAbility("{1}{R}{R}"));
    }

    private VugLizard(final VugLizard card) {
        super(card);
    }

    @Override
    public VugLizard copy() {
        return new VugLizard(this);
    }

}