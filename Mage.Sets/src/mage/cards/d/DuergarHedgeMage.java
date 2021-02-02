
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DuergarHedgeMage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Mountain");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("a Plains");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter2.add(SubType.PLAINS.getPredicate());
    }
    private static final String rule1 = "When {this} enters the battlefield, if you control two or more Mountains, you may destroy target artifact.";
    private static final String rule2 = "When {this} enters the battlefield, if you control two or more Plains, you may destroy target enchantment.";

    public DuergarHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Duergar Hedge-Mage enters the battlefield, if you control two or more Mountains, you may destroy target artifact.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule1);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // When Duergar Hedge-Mage enters the battlefield, if you control two or more Plains, you may destroy target enchantment.
        Ability ability2 = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true), new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1), rule2);
        ability2.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability2);

    }

    private DuergarHedgeMage(final DuergarHedgeMage card) {
        super(card);
    }

    @Override
    public DuergarHedgeMage copy() {
        return new DuergarHedgeMage(this);
    }
}
