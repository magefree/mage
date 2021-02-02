
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox

 */
public final class Thoughtleech extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an Island an opponent controls");

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Thoughtleech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{G}");

        // Whenever an Island an opponent controls becomes tapped, you may gain 1 life.
        this.addAbility(new BecomesTappedTriggeredAbility(new GainLifeEffect(1), true, filter));
    }

    private Thoughtleech(final Thoughtleech card) {
        super(card);
    }

    @Override
    public Thoughtleech copy() {
        return new Thoughtleech(this);
    }
}
