
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
public final class Lifeblood extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Mountain an opponent controls");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Lifeblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Whenever a Mountain an opponent controls becomes tapped, you gain 1 life.
        this.addAbility(new BecomesTappedTriggeredAbility(new GainLifeEffect(1), false, filter));
    }

    private Lifeblood(final Lifeblood card) {
        super(card);
    }

    @Override
    public Lifeblood copy() {
        return new Lifeblood(this);
    }
}
