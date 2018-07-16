
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author fireshoes
 */
public final class NeckBreaker extends CardImpl {

    public NeckBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        // Attacking creatures you control get +1/+0 and have trample.
        SimpleStaticAbility boostAbility = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, new FilterAttackingCreature()),
                new TransformedCondition(false), "Attacking creatures you control get +1/+0"));
        boostAbility.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, new FilterAttackingCreature()));
        this.addAbility(boostAbility);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Neck Breaker.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public NeckBreaker(final NeckBreaker card) {
        super(card);
    }

    @Override
    public NeckBreaker copy() {
        return new NeckBreaker(this);
    }
}
