
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Styxo
 */
public final class TrooperCommando extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures");

    static {
        filter.add(new SubtypePredicate(SubType.TROOPER));
    }

    public TrooperCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trooper creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));

    }

    public TrooperCommando(final TrooperCommando card) {
        super(card);
    }

    @Override
    public TrooperCommando copy() {
        return new TrooperCommando(this);
    }
}
