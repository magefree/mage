
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class WintersNight extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("a player taps a snow land");
    {
        filter.add(new SupertypePredicate(SuperType.SNOW));
    }

    public WintersNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{G}{W}");
        addSuperType(SuperType.WORLD);

        // Whenever a player taps a snow land for mana, that player adds one mana of any type that land produced.
        // That land doesn't untap during its controller's next untap step.
        ManaEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("that player adds one mana of any type that land produced");
        Ability ability = new TapForManaAllTriggeredManaAbility(effect, filter, SetTargetPointer.PERMANENT);
        Effect effect2 = new DontUntapInControllersNextUntapStepTargetEffect();
        effect2.setText("That land doesn't untap during its controller's next untap step");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public WintersNight(final WintersNight card) {
        super(card);
    }

    @Override
    public WintersNight copy() {
        return new WintersNight(this);
    }
}
