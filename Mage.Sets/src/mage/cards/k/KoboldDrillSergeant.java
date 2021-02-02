
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author ilcartographer
 */
public final class KoboldDrillSergeant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Kobold creatures");

    static {
        filter.add(SubType.KOBOLD.getPredicate());
    }

    public KoboldDrillSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.KOBOLD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Other Kobold creatures you control get +0/+1 and have trample.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter, true));
        Effect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and have trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private KoboldDrillSergeant(final KoboldDrillSergeant card) {
        super(card);
    }

    @Override
    public KoboldDrillSergeant copy() {
        return new KoboldDrillSergeant(this);
    }
}
