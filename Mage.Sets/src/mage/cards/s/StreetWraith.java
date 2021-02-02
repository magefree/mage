
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class StreetWraith extends CardImpl {

    public StreetWraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.WRAITH);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
        // Cycling-Pay 2 life.
        this.addAbility(new CyclingAbility(new PayLifeCost(2)));
    }

    private StreetWraith(final StreetWraith card) {
        super(card);
    }

    @Override
    public StreetWraith copy() {
        return new StreetWraith(this);
    }
}
