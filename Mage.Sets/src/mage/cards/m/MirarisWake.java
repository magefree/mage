
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class MirarisWake extends CardImpl {

    public MirarisWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{W}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));

        // Whenever you tap a land for mana, add one mana of any type that land produced.
        AddManaOfAnyTypeProducedEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("add one mana of any type that land produced");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect,
                new FilterControlledLandPermanent("you tap a land"),
                SetTargetPointer.PERMANENT));

    }

    private MirarisWake(final MirarisWake card) {
        super(card);
    }

    @Override
    public MirarisWake copy() {
        return new MirarisWake(this);
    }
}
