package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrakusethMawOfFlames extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterAnyTarget("any target");

    static {
        filter.getPlayerFilter().add(new AnotherTargetPredicate(2, true));
        filter.getPermanentFilter().add(new AnotherTargetPredicate(2, true));
    }

    public DrakusethMawOfFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Drakuseth, Maw of Flames attacks, it deals 4 damage to any target and 3 damage to each of up to two other targets.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetAndTargetEffect(4, 3)
                .setText("it deals 4 damage to any target and 3 damage to each of up to two other targets"));
        ability.addTarget(new TargetAnyTarget().withChooseHint("to deal 4 damage").setTargetTag(1));
        ability.addTarget(new TargetPermanentOrPlayer(
                0, 2, filter, false
        ).withChooseHint("to deal 3 damage").setTargetTag(2));
        this.addAbility(ability);
    }

    private DrakusethMawOfFlames(final DrakusethMawOfFlames card) {
        super(card);
    }

    @Override
    public DrakusethMawOfFlames copy() {
        return new DrakusethMawOfFlames(this);
    }
}
