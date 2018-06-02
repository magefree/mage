
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
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
 * @author Loki
 */
public final class DrogskolCaptain extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Spirit");

    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public DrogskolCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Other Spirit creatures you control get +1/+1 and have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
    }

    public DrogskolCaptain(final DrogskolCaptain card) {
        super(card);
    }

    @Override
    public DrogskolCaptain copy() {
        return new DrogskolCaptain(this);
    }
}
