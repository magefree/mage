
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class MajesticHeliopterus extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target Dinosaur you control");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.DINOSAUR.getPredicate());
    }
    public MajesticHeliopterus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Majestic Heliopterus attacks, another target Dinosaur you control gains flying until end of turn.
        Effect effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        Ability ability = new AttacksTriggeredAbility(effect, false);
        Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private MajesticHeliopterus(final MajesticHeliopterus card) {
        super(card);
    }

    @Override
    public MajesticHeliopterus copy() {
        return new MajesticHeliopterus(this);
    }
}
