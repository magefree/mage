package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class HeavenlyQilin extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public HeavenlyQilin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KIRIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenenver Heavenly Qilin attacks, another target creature you control gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new GainAbilityTargetEffect(
                        FlyingAbility.getInstance(),
                        Duration.EndOfTurn
                ), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1, filter, true));
        this.addAbility(ability);
    }

    public HeavenlyQilin(final HeavenlyQilin card) {
        super(card);
    }

    @Override
    public HeavenlyQilin copy() {
        return new HeavenlyQilin(this);
    }
}
