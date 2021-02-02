
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MonocoloredPredicate;

/**
 *
 * @author LevelX2
 */
public final class GuardianOfTheGuildpact extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("monocolored");

    static {
        filter.add(MonocoloredPredicate.instance);
    }

    public GuardianOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Protection from monocolored
        this.addAbility(new ProtectionAbility(filter));
    }

    private GuardianOfTheGuildpact(final GuardianOfTheGuildpact card) {
        super(card);
    }

    @Override
    public GuardianOfTheGuildpact copy() {
        return new GuardianOfTheGuildpact(this);
    }
}
