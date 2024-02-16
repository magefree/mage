package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInAllHandsCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WerewolfOfAncientHunger extends CardImpl {

    public WerewolfOfAncientHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setGreen(true);

        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Werewolf of Ancient Hunger's power and toughness are each equal to the total number of cards in all players' hands.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new ConditionalContinuousEffect(new SetBasePowerToughnessSourceEffect(CardsInAllHandsCount.instance),
                        new TransformedCondition(false), "{this}'s power and toughness are each equal to the total number of cards in all players' hands")));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf of Ancient Hunger.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private WerewolfOfAncientHunger(final WerewolfOfAncientHunger card) {
        super(card);
    }

    @Override
    public WerewolfOfAncientHunger copy() {
        return new WerewolfOfAncientHunger(this);
    }
}
