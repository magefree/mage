
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class WarrenScourgeElf extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblin");

    static {
        filter.add(new SubtypePredicate(SubType.GOBLIN));
    }

    public WarrenScourgeElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from Goblins
        this.addAbility(new ProtectionAbility(filter));
    }

    public WarrenScourgeElf(final WarrenScourgeElf card) {
        super(card);
    }

    @Override
    public WarrenScourgeElf copy() {
        return new WarrenScourgeElf(this);
    }
}
