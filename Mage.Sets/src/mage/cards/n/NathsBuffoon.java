
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author LoneFox
 */
public final class NathsBuffoon extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elves");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public NathsBuffoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from Elves
        this.addAbility(new ProtectionAbility(filter));
    }

    private NathsBuffoon(final NathsBuffoon card) {
        super(card);
    }

    @Override
    public NathsBuffoon copy() {
        return new NathsBuffoon(this);
    }
}
