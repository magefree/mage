
package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author nantuko
 */
public final class RecklessWaif extends CardImpl {

    public RecklessWaif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WEREWOLF);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.m.MercilessPredator.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Reckless Waif.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public RecklessWaif(final RecklessWaif card) {
        super(card);
    }

    @Override
    public RecklessWaif copy() {
        return new RecklessWaif(this);
    }
}
