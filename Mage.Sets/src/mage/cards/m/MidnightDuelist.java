
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author noxx

 */
public final class MidnightDuelist extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Vampires");

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public MidnightDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from Vampires
        this.addAbility(new ProtectionAbility(filter));
    }

    public MidnightDuelist(final MidnightDuelist card) {
        super(card);
    }

    @Override
    public MidnightDuelist copy() {
        return new MidnightDuelist(this);
    }
}
