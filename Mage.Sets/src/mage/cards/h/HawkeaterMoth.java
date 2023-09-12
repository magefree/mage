

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class HawkeaterMoth extends CardImpl {

    public HawkeaterMoth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    private HawkeaterMoth(final HawkeaterMoth card) {
        super(card);
    }

    @Override
    public HawkeaterMoth copy() {
        return new HawkeaterMoth(this);
    }

}
