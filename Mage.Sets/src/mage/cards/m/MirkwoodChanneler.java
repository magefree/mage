package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MirkwoodChanneler extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.ELF, "Elf you control");

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.FOREST, "Forests you control"), null
    );
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    public MirkwoodChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target Elf you control gains trample and gets +X/+X until end of turn, where X is the number of Forests you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(TrampleAbility.getInstance())
                        .setText("target Elf you control gains trample"),
                TargetController.YOU, false
        );
        ability.addEffect(new BoostTargetEffect(xValue, xValue)
                .setText("and gets +X/+X until end of turn, where X is the number of Forests you control")
        );
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private MirkwoodChanneler(final MirkwoodChanneler card) {
        super(card);
    }

    @Override
    public MirkwoodChanneler copy() {
        return new MirkwoodChanneler(this);
    }
}
