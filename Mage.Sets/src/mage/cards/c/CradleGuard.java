

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class CradleGuard extends CardImpl {

    public CradleGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

    this.addAbility(TrampleAbility.getInstance());
    this.addAbility(new EchoAbility("{1}{G}{G}"));
    }

    private CradleGuard(final CradleGuard card) {
        super(card);
    }

    @Override
    public CradleGuard copy() {
        return new CradleGuard(this);
    }

}