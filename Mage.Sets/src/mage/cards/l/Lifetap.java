
package mage.cards.l;

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
public final class Lifetap extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Forest an opponent controls");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Lifetap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");

        // Whenever a Forest an opponent controls becomes tapped, you gain 1 life.
        this.addAbility(new BecomesTappedTriggeredAbility(new GainLifeEffect(1), false, filter));
    }

    private Lifetap(final Lifetap card) {
        super(card);
    }

    @Override
    public Lifetap copy() {
        return new Lifetap(this);
    }
}
