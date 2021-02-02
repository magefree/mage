
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class GuulDrazVampire extends CardImpl {

    private static final String rule1 = "As long as an opponent has 10 or less life, {this} gets +2/+1";
    private static final String rule2 = "and has intimidate. <i>(It can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>";

    public GuulDrazVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as an opponent has 10 or less life, Guul Draz Vampire gets +2/+1 and has intimidate. (It can't be blocked except by artifact creatures and/or creatures that share a color with it.)
        Condition condition = new XorLessLifeCondition(XorLessLifeCondition.CheckType.AN_OPPONENT, 10);
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield), condition, rule1);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(IntimidateAbility.getInstance()), condition, rule2));
        this.addAbility(ability);

    }

    private GuulDrazVampire(final GuulDrazVampire card) {
        super(card);
    }

    @Override
    public GuulDrazVampire copy() {
        return new GuulDrazVampire(this);
    }
}
