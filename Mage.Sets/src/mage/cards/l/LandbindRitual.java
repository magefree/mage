
package mage.cards.l;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class LandbindRitual extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains you control");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LandbindRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)));
    }

    public LandbindRitual(final LandbindRitual card) {
        super(card);
    }

    @Override
    public LandbindRitual copy() {
        return new LandbindRitual(this);
    }
}
