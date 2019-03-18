
package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author North, noxx
 */
public final class MayorOfAvabruck extends CardImpl {

    private static final String ruleText = "Other Human creatures you control get +1/+1";

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human creatures");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public MayorOfAvabruck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.WEREWOLF);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.h.HowlpackAlpha.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Other Human creatures you control get +1/+1.
        Effect effect = new ConditionalContinuousEffect(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true), new InvertCondition(new TransformedCondition()), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mayor of Avabruck.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public MayorOfAvabruck(final MayorOfAvabruck card) {
        super(card);
    }

    @Override
    public MayorOfAvabruck copy() {
        return new MayorOfAvabruck(this);
    }
}
