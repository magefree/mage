
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledEnchantmentPermanent;

/**
 *
 * @author fireshoes
 */
public final class BloodCursedKnight extends CardImpl {

    static final private String rule1 = "As long as you control an enchantment, {this} gets +1/+1";
    static final private String rule2 = "and has lifelink";

    public BloodCursedKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as you control an enchantment, Blood-Cursed Knight gets +1/+1 and has lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledEnchantmentPermanent()), rule1));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledEnchantmentPermanent()), rule2));
        this.addAbility(ability);
    }

    private BloodCursedKnight(final BloodCursedKnight card) {
        super(card);
    }

    @Override
    public BloodCursedKnight copy() {
        return new BloodCursedKnight(this);
    }
}
