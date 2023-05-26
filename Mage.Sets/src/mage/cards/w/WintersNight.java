package mage.cards.w;

import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class WintersNight extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a player taps a snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public WintersNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}{W}");
        this.supertype.add(SuperType.WORLD);

        // Whenever a player taps a snow land for mana, that player adds one mana of any type that land produced.
        // That land doesn't untap during its controller's next untap step.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect().setText("that player adds one mana of any type that land produced"),
                filter,
                SetTargetPointer.PERMANENT
        ));

        this.addAbility(new TapForManaAllTriggeredAbility(
                new DontUntapInControllersNextUntapStepTargetEffect().setText("that land doesn't untap during its controller's next untap step"),
                filter,
                SetTargetPointer.PERMANENT
        ));
    }

    private WintersNight(final WintersNight card) {
        super(card);
    }

    @Override
    public WintersNight copy() {
        return new WintersNight(this);
    }
}
