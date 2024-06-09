

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PlagueMyr extends CardImpl {

    public PlagueMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new ColorlessManaAbility());
    }

    private PlagueMyr(final PlagueMyr card) {
        super(card);
    }

    @Override
    public PlagueMyr copy() {
        return new PlagueMyr(this);
    }

}
