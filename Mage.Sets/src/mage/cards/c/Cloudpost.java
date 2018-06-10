
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author jonubuu
 */
public final class Cloudpost extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Locus on the battlefield");

    static {
        filter.add(new SubtypePredicate(SubType.LOCUS));
    }

    public Cloudpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.LOCUS);

        // Cloudpost enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {C} for each Locus on the battlefield.
        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana(1), new PermanentsOnBattlefieldCount(filter)));
    }

    public Cloudpost(final Cloudpost card) {
        super(card);
    }

    @Override
    public Cloudpost copy() {
        return new Cloudpost(this);
    }
}
