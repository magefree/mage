package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DuergarHedgeMage extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.MOUNTAIN, "you control two or more Mountains"),
            ComparisonType.MORE_THAN, 1
    );
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.PLAINS, "you control two or more Plains"),
            ComparisonType.MORE_THAN, 1
    );

    public DuergarHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Duergar Hedge-Mage enters the battlefield, if you control two or more Mountains, you may destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true).withInterveningIf(condition);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // When Duergar Hedge-Mage enters the battlefield, if you control two or more Plains, you may destroy target enchantment.
        ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true).withInterveningIf(condition2);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private DuergarHedgeMage(final DuergarHedgeMage card) {
        super(card);
    }

    @Override
    public DuergarHedgeMage copy() {
        return new DuergarHedgeMage(this);
    }
}
