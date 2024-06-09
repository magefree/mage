package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EldraziLinebreaker extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ELDRAZI));
    private static final Hint hint = new ValueHint("Eldrazi you control", xValue);

    public EldraziLinebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{C}{R}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, target creature you control gains haste and gets +X/+0 until end of turn, where X is the number of Eldrazi you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(HasteAbility.getInstance())
                        .setText("target creature you control gains haste"),
                TargetController.YOU, false
        );
        ability.addEffect(new BoostTargetEffect(xValue, StaticValue.get(0))
                .setText("and gets +X/+0 until end of turn, where X is the number of Eldrazi you control"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(hint));
    }

    private EldraziLinebreaker(final EldraziLinebreaker card) {
        super(card);
    }

    @Override
    public EldraziLinebreaker copy() {
        return new EldraziLinebreaker(this);
    }
}
