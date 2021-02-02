

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class HeraldOfSerra extends CardImpl {

    public HeraldOfSerra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(new EchoAbility("{2}{W}{W}"));
    }

    private HeraldOfSerra(final HeraldOfSerra card) {
        super(card);
    }

    @Override
    public HeraldOfSerra copy() {
        return new HeraldOfSerra(this);
    }

}