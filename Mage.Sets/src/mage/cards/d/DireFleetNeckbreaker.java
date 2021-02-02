
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author JayDi85
 */
public final class DireFleetNeckbreaker extends CardImpl {

    private static final FilterAttackingCreature filterYourAttackingPirates = new FilterAttackingCreature("Attacking Pirates");
    static {
        filterYourAttackingPirates.add(TargetController.YOU.getControllerPredicate());
        filterYourAttackingPirates.add(SubType.PIRATE.getPredicate());
    }

    public DireFleetNeckbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Attacking Pirates you control get +2/+0.
        GainAbilityControlledEffect gainEffect = new GainAbilityControlledEffect(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.Custom)),
                Duration.WhileOnBattlefield,
                filterYourAttackingPirates,
                false
        );
        gainEffect.setText("Attacking Pirates you control get +2/+0.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, gainEffect));
    }

    private DireFleetNeckbreaker(final DireFleetNeckbreaker card) {
        super(card);
    }

    @Override
    public DireFleetNeckbreaker copy() {
        return new DireFleetNeckbreaker(this);
    }
}