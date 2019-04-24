

package mage.cards.h;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfCleansingFire extends CardImpl {

    final static FilterControlledPermanent filter = new FilterControlledPermanent("Shrine");

    static {
        filter.add(new SubtypePredicate(SubType.SHRINE));
    }

    public HondenOfCleansingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);


        // At the beginning of your upkeep, you gain 2 life for each Shrine you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)), TargetController.YOU, false));
    }

    public HondenOfCleansingFire(final HondenOfCleansingFire card) {
        super(card);
    }

    @Override
    public HondenOfCleansingFire copy() {
        return new HondenOfCleansingFire(this);
    }

}
