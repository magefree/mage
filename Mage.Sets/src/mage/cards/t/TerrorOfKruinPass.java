
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class TerrorOfKruinPass extends CardImpl {

    private static final String ruleText = "Werewolves you control have menace. (They can't be blocked except by two or more creatures.)";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.WEREWOLF));
    }

    public TerrorOfKruinPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DoubleStrikeAbility.getInstance());
        // Werewolves you control have menace. (They can't be blocked except by two or more creatures.)
        Effect effect = new ConditionalContinuousEffect(new GainAbilityAllEffect(new MenaceAbility(), Duration.Custom, filter), new TransformedCondition(), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Terror of Kruin Pass.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public TerrorOfKruinPass(final TerrorOfKruinPass card) {
        super(card);
    }

    @Override
    public TerrorOfKruinPass copy() {
        return new TerrorOfKruinPass(this);
    }
}
