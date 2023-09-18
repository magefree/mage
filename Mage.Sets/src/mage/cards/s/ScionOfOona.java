
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ScionOfOona extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Faeries");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("Faerie creatures");

    static {
        filter.add(SubType.FAERIE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filterCreature.add(SubType.FAERIE.getPredicate());
        filterCreature.add(TargetController.YOU.getControllerPredicate());
    }

    public ScionOfOona(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Other Faerie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterCreature, true)));
        // Other Faeries you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
    }

    private ScionOfOona(final ScionOfOona card) {
        super(card);
    }

    @Override
    public ScionOfOona copy() {
        return new ScionOfOona(this);
    }
}
