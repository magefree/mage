
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LeafGilder extends CardImpl {

    public LeafGilder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new GreenManaAbility());
    }

    private LeafGilder(final LeafGilder card) {
        super(card);
    }

    @Override
    public LeafGilder copy() {
        return new LeafGilder(this);
    }
}
