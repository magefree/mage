
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SculptingSteel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public SculptingSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may have Sculpting Steel enter the battlefield as a copy of any artifact on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(filter), true));
    }

    private SculptingSteel(final SculptingSteel card) {
        super(card);
    }

    @Override
    public SculptingSteel copy() {
        return new SculptingSteel(this);
    }
}
