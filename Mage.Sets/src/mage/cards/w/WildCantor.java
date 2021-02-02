
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WildCantor extends CardImpl {

    public WildCantor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new AnyColorManaAbility(new SacrificeSourceCost()));
    }

    private WildCantor(final WildCantor card) {
        super(card);
    }

    @Override
    public WildCantor copy() {
        return new WildCantor(this);
    }
}
