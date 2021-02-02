
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CrownedCeratok extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature you control with a +1/+1 counter on it");
    static {
        filter.add(CounterType.P1P1.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CrownedCeratok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Each creature you control with a +1/+1 counter on it has trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

    }

    private CrownedCeratok(final CrownedCeratok card) {
        super(card);
    }

    @Override
    public CrownedCeratok copy() {
        return new CrownedCeratok(this);
    }
}
