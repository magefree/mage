package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarshalOfTheLost extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURES, null);
    private static final Hint hint = new ValueHint("Attacking creatures", xValue);

    public MarshalOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you attack, target creature gets +X/+X until end of turn, where X is the number of attacking creatures.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(xValue, xValue), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(hint));
    }

    private MarshalOfTheLost(final MarshalOfTheLost card) {
        super(card);
    }

    @Override
    public MarshalOfTheLost copy() {
        return new MarshalOfTheLost(this);
    }
}
