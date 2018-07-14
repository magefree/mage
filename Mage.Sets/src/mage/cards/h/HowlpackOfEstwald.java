
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
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
public final class HowlpackOfEstwald extends CardImpl {

    public HowlpackOfEstwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack of Estwald.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public HowlpackOfEstwald(final HowlpackOfEstwald card) {
        super(card);
    }

    @Override
    public HowlpackOfEstwald copy() {
        return new HowlpackOfEstwald(this);
    }
}
