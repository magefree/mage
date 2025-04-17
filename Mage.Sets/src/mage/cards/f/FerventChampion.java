package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.ReduceCostEquipTargetSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FerventChampion extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.KNIGHT, "another target attacking Knight you control");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    public FerventChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Fervent Champion attacks, another target attacking Knight you control gets +1/+0 until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(1, 0, Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Equip abilities you activate that target Fervent Champion cost {3} less to activate.
        this.addAbility(new SimpleStaticAbility(new ReduceCostEquipTargetSourceEffect(3)));
    }

    private FerventChampion(final FerventChampion card) {
        super(card);
    }

    @Override
    public FerventChampion copy() {
        return new FerventChampion(this);
    }
}
