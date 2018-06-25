
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author North, noxx
 */
public final class HowlpackAlpha extends CardImpl {

    private static final String ruleText = "At the beginning of your end step, create a 2/2 green Wolf creature token";

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf and Wolf creatures");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.WEREWOLF), new SubtypePredicate(SubType.WOLF)));
    }

    public HowlpackAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Werewolf and Wolf creatures you control get +1/+1.
        Effect effect = new ConditionalContinuousEffect(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true), new TransformedCondition(), null);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of your end step, create a 2/2 green Wolf creature token.
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new WolfToken()), false), new TransformedCondition(), ruleText));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack Alpha.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public HowlpackAlpha(final HowlpackAlpha card) {
        super(card);
    }

    @Override
    public HowlpackAlpha copy() {
        return new HowlpackAlpha(this);
    }
}
