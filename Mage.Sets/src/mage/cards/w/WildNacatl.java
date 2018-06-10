

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class WildNacatl extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Mountain");
    private static final FilterPermanent filter2 = new FilterPermanent("Plains");

    static {
        filter1.add(new SubtypePredicate(SubType.MOUNTAIN));
        filter2.add(new SubtypePredicate(SubType.PLAINS));
    }

    public WildNacatl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Wild Nacatl gets +1/+1 as long as you control a Mountain.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter1, 1, 1)));
        
        // Wild Nacatl gets +1/+1 as long as you control a Plains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter2, 1, 1)));
    }

    public WildNacatl(final WildNacatl card) {
        super(card);
    }

    @Override
    public WildNacatl copy() {
        return new WildNacatl(this);
    }
}
