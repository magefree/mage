
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HeraldOfTheHost extends CardImpl {

    public HeraldOfTheHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Myriad
        this.addAbility(new MyriadAbility());
    }

    private HeraldOfTheHost(final HeraldOfTheHost card) {
        super(card);
    }

    @Override
    public HeraldOfTheHost copy() {
        return new HeraldOfTheHost(this);
    }
}
