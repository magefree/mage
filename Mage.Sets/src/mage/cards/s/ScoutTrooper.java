
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
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
public final class ScoutTrooper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures");

    static {
        filter.add(new SubtypePredicate(SubType.TROOPER));
    }

    public ScoutTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trooper creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

    }

    public ScoutTrooper(final ScoutTrooper card) {
        super(card);
    }

    @Override
    public ScoutTrooper copy() {
        return new ScoutTrooper(this);
    }
}
