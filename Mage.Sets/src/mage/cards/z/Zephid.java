

package mage.cards.z;


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
public final class Zephid extends CardImpl {

    public Zephid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    private Zephid(final Zephid card) {
        super(card);
    }

    @Override
    public Zephid copy() {
        return new Zephid(this);
    }
}
