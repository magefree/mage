
package mage.cards.r;

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
 * @author anonymous
 */
public final class RibbonsOfTheReikai extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Spirit you control");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public RibbonsOfTheReikai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");
        this.subtype.add(SubType.ARCANE);


        // Draw a card for each Spirit you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private RibbonsOfTheReikai(final RibbonsOfTheReikai card) {
        super(card);
    }

    @Override
    public RibbonsOfTheReikai copy() {
        return new RibbonsOfTheReikai(this);
    }
}
