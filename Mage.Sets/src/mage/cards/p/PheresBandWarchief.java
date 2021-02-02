
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PheresBandWarchief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other Centaur creatures you control");

    static {
        filter.add(SubType.CENTAUR.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PheresBandWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Other Centaur creatures you control get +1/+1 and have vigilance and trample.
        Effect effect = new BoostControlledEffect(1,1,Duration.WhileOnBattlefield, filter, true);
        effect.setText("Other Centaur creatures you control get +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and have vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("and trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private PheresBandWarchief(final PheresBandWarchief card) {
        super(card);
    }

    @Override
    public PheresBandWarchief copy() {
        return new PheresBandWarchief(this);
    }
}
