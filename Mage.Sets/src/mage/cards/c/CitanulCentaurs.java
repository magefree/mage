

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class CitanulCentaurs extends CardImpl {

    public CitanulCentaurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CENTAUR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

    this.addAbility(ShroudAbility.getInstance());
    this.addAbility(new EchoAbility("{3}{G}"));
    }

    private CitanulCentaurs(final CitanulCentaurs card) {
        super(card);
    }

    @Override
    public CitanulCentaurs copy() {
        return new CitanulCentaurs(this);
    }

}