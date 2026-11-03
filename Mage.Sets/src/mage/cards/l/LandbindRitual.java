
package mage.cards.l;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author North
 */
public final class LandbindRitual extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Plains you control");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public LandbindRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)));
    }

    private LandbindRitual(final LandbindRitual card) {
        super(card);
    }

    @Override
    public LandbindRitual copy() {
        return new LandbindRitual(this);
    }
}
