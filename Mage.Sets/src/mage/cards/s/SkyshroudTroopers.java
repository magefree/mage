
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SkyshroudTroopers extends CardImpl {

    public SkyshroudTroopers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private SkyshroudTroopers(final SkyshroudTroopers card) {
        super(card);
    }

    @Override
    public SkyshroudTroopers copy() {
        return new SkyshroudTroopers(this);
    }
}
