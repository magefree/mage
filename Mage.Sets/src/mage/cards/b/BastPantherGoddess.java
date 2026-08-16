package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author muz
 */
public final class BastPantherGoddess extends CardImpl {

    public BastPantherGoddess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Bast can't attack or block unless you control three or more creatures.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockUnlessConditionSourceEffect(
            new PermanentsOnTheBattlefieldCondition(
                StaticFilters.FILTER_PERMANENT_CREATURES, ComparisonType.OR_GREATER, 3
            )
        ).setText("{this} can't attack or block unless you control three or more creatures")));

        // Whenever you attack, target attacking creature gets +X/+X until end of turn, where X is the number of creatures you control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(
            CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL
        ).setText("target attacking creature gets +X/+X until end of turn, where X is the number of creatures you control"), 1);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private BastPantherGoddess(final BastPantherGoddess card) {
        super(card);
    }

    @Override
    public BastPantherGoddess copy() {
        return new BastPantherGoddess(this);
    }
}
