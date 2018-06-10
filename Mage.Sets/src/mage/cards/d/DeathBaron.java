
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class DeathBaron extends CardImpl {

    private static final FilterCreaturePermanent filterSkeletons = new FilterCreaturePermanent("Skeleton creatures");
    private static final FilterCreaturePermanent filterZombie = new FilterCreaturePermanent("Zombie creatures");

    static {
        filterSkeletons.add(new SubtypePredicate(SubType.SKELETON));
        filterZombie.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public DeathBaron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Skeleton creatures you control and other Zombie creatures you control get +1/+1 and have deathtouch.
        Ability firstPart = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterSkeletons, false));
        firstPart.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filterSkeletons, false));
        this.addAbility(firstPart);
        Ability secondPart = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterZombie, true));
        secondPart.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filterZombie, true));
        this.addAbility(secondPart);
    }

    public DeathBaron(final DeathBaron card) {
        super(card);
    }

    @Override
    public DeathBaron copy() {
        return new DeathBaron(this);
    }
}
