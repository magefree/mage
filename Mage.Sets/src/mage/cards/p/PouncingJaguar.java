

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class PouncingJaguar extends CardImpl {

    public PouncingJaguar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    this.addAbility(new EchoAbility("{G}"));
    }

    private PouncingJaguar(final PouncingJaguar card) {
        super(card);
    }

    @Override
    public PouncingJaguar copy() {
        return new PouncingJaguar(this);
    }

}