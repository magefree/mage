
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class FleshmadSteed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public FleshmadSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HORSE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature dies, tap Fleshmad Steed.
        this.addAbility(new DiesCreatureTriggeredAbility(new TapSourceEffect(), false, filter));
    }

    private FleshmadSteed(final FleshmadSteed card) {
        super(card);
    }

    @Override
    public FleshmadSteed copy() {
        return new FleshmadSteed(this);
    }
}
