
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author North
 */
public final class ScornedVillager extends CardImpl {

    public ScornedVillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.m.MoonscarredWerewolf.class;

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
        // At the beginning of each upkeep, if no spells were cast last turn, transform Scorned Villager.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability,
                NoSpellsWereCastLastTurnCondition.instance,
                TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public ScornedVillager(final ScornedVillager card) {
        super(card);
    }

    @Override
    public ScornedVillager copy() {
        return new ScornedVillager(this);
    }
}
