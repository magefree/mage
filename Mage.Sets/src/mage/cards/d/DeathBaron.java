package mage.cards.d;

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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 * @author Loki
 */
public final class DeathBaron extends CardImpl {

    private static final FilterCreaturePermanent filterSkeletons = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterZombie = new FilterCreaturePermanent();

    static {
        filterSkeletons.add(new SubtypePredicate(SubType.SKELETON));
        filterZombie.add(new SubtypePredicate(SubType.ZOMBIE));
        filterZombie.add(Predicates.not(new SubtypePredicate(SubType.SKELETON)));
    }

    public DeathBaron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Skeletons you control and other Zombies you control get +1/+1 and have deathtouch.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filterSkeletons, false
        ).setText("Skeletons you control"));
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filterSkeletons, false
        ).setText("and other Zombies you control"));
        ability.addEffect(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filterZombie, true
        ).setText("get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filterZombie, true
        ).setText("and have deathtouch"));
        this.addAbility(ability);
    }

    private DeathBaron(final DeathBaron card) {
        super(card);
    }

    @Override
    public DeathBaron copy() {
        return new DeathBaron(this);
    }
}
