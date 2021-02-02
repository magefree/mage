
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class HundredHandedOne extends CardImpl {

    public HundredHandedOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {3}{W}{W}{W}: Monstrosity 3. <i>(If this creature isn't monstrous, put three +1/+1 counters on it and it becomes monstrous.)</i>
        this.addAbility(new MonstrosityAbility("{3}{W}{W}{W}", 3));

        // As long as Hundred-Handed One is monstrous, it has reach and can block an additional ninety-nine creatures each combat.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.WhileOnBattlefield), 
                MonstrousCondition.instance,
                "As long as Hundred-Handed One is monstrous, it has reach");
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(
                new CanBlockAdditionalCreatureEffect(99), 
                MonstrousCondition.instance,
                "and can block an additional ninety-nine creatures each combat");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        this.addAbility(ability);

    }

    private HundredHandedOne(final HundredHandedOne card) {
        super(card);
    }

    @Override
    public HundredHandedOne copy() {
        return new HundredHandedOne(this);
    }
}
