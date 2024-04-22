package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class Sanctimony extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an opponent taps a Mountain");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public Sanctimony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Whenever an opponent taps a Mountain for mana, you may gain 1 life.
        this.addAbility(new TapForManaAllTriggeredAbility(new GainLifeEffect(1), filter, SetTargetPointer.NONE, true));
    }

    private Sanctimony(final Sanctimony card) {
        super(card);
    }

    @Override
    public Sanctimony copy() {
        return new Sanctimony(this);
    }
}
