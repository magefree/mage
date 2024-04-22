
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.special.JohanVigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Johan extends CardImpl {

    public Johan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, you may have Johan gain "Johan can't attack" until end of combat. If you do, attacking doesn't cause creatures you control to tap this combat if Johan is untapped.
        Condition condition = new CompoundCondition("if {this} is untapped",
                SourceTappedCondition.UNTAPPED,
                SourceOnBattlefieldCondition.instance);
        Ability ability = new BeginningOfCombatTriggeredAbility(new CantAttackSourceEffect(Duration.EndOfCombat).setText("you may have {this} gain \"{this} can't attack\" until end of combat"), TargetController.YOU, true);
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilityControlledEffect(JohanVigilanceAbility.getInstance(), Duration.EndOfCombat, new FilterControlledCreaturePermanent("creatures")),
            condition, 
            "If you do, attacking doesn't cause creatures you control to tap this combat if {this} is untapped"));
        this.addAbility(ability);
    }

    private Johan(final Johan card) {
        super(card);
    }

    @Override
    public Johan copy() {
        return new Johan(this);
    }
}
