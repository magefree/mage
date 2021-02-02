
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author noxx
 */
public final class MassAppeal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Human you control");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public MassAppeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");


        // Draw a card for each Human you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private MassAppeal(final MassAppeal card) {
        super(card);
    }

    @Override
    public MassAppeal copy() {
        return new MassAppeal(this);
    }
}
