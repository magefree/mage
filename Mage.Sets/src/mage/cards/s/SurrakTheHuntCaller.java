
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SurrakTheHuntCaller extends CardImpl {

    public SurrakTheHuntCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // <i>Formidable</i> &mdash; At the beginning of combat on your turn, if creatures you control have total power 8 or greater, target creature you control gains haste until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), TargetController.YOU, false),
                FormidableCondition.instance,
                "<i>Formidable</i> &mdash; At the beginning of combat on your turn, if creatures you control have total power 8 or greater, target creature you control gains haste until end of turn.");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SurrakTheHuntCaller(final SurrakTheHuntCaller card) {
        super(card);
    }

    @Override
    public SurrakTheHuntCaller copy() {
        return new SurrakTheHuntCaller(this);
    }
}
