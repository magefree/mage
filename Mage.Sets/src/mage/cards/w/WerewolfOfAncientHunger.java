
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInAllHandsCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class WerewolfOfAncientHunger extends CardImpl {

    public WerewolfOfAncientHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setGreen(true);

        this.nightCard = true;
        this.transformable = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Werewolf of Ancient Hunger's power and toughness are each equal to the total number of cards in all players' hands.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, 
                new ConditionalContinuousEffect(new SetPowerToughnessSourceEffect(new CardsInAllHandsCount(), Duration.EndOfGame),
                new TransformedCondition(false), "{this}'s power and toughness are each equal to the total number of cards in all players' hands")));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf of Ancient Hunger.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public WerewolfOfAncientHunger(final WerewolfOfAncientHunger card) {
        super(card);
    }

    @Override
    public WerewolfOfAncientHunger copy() {
        return new WerewolfOfAncientHunger(this);
    }
}
