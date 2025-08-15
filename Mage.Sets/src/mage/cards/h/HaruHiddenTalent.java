
package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class HaruHiddenTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY, "another Ally you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HaruHiddenTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Ally you control enters, earthbend 1.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new EarthbendTargetEffect(1), filter
        ));
    }

    private HaruHiddenTalent(final HaruHiddenTalent card) {
        super(card);
    }

    @Override
    public HaruHiddenTalent copy() {
        return new HaruHiddenTalent(this);
    }
}
