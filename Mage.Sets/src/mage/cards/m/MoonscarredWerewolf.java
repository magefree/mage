
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class MoonscarredWerewolf extends CardImpl {

    public MoonscarredWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.addAbility(VigilanceAbility.getInstance());
        // {tap}: Add {G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()));
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Moonscarred Werewolf.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability,
                TwoOrMoreSpellsWereCastLastTurnCondition.instance,
                TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public MoonscarredWerewolf(final MoonscarredWerewolf card) {
        super(card);
    }

    @Override
    public MoonscarredWerewolf copy() {
        return new MoonscarredWerewolf(this);
    }
}
