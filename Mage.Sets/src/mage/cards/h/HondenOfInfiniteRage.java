package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class HondenOfInfiniteRage extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SHRINE)
    );
    private static final Hint hint = new ValueHint("Shrines you control", xValue);

    public HondenOfInfiniteRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, Honden of Infinite Rage deals damage to any target equal to the number of Shrines you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(xValue), TargetController.YOU, false).addHint(hint);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HondenOfInfiniteRage(final HondenOfInfiniteRage card) {
        super(card);
    }

    @Override
    public HondenOfInfiniteRage copy() {
        return new HondenOfInfiniteRage(this);
    }

}
