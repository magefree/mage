package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrFarenTheHengehammer extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);
    private static final FilterPermanent filter
            = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SyrFarenTheHengehammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Syr Faren, the Hengehammer attacks, another target attacking creature gets +X/+X until end of turn, where X is Syr Faren's power.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SyrFarenTheHengehammer(final SyrFarenTheHengehammer card) {
        super(card);
    }

    @Override
    public SyrFarenTheHengehammer copy() {
        return new SyrFarenTheHengehammer(this);
    }
}
