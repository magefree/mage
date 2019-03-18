
package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterAttackingCreature;

/**
 * @author nantuko
 */
public final class InstigatorGang extends CardImpl {

    public InstigatorGang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.w.WildbloodPack.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Attacking creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, new FilterAttackingCreature()),
                        new TransformedCondition(true), "Attacking creatures you control get +1/+0")));


        // At the beginning of each upkeep, if no spells were cast last turn, transform Instigator Gang.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public InstigatorGang(final InstigatorGang card) {
        super(card);
    }

    @Override
    public InstigatorGang copy() {
        return new InstigatorGang(this);
    }
}
