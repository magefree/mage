
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class SageOfAncientLore extends CardImpl {

    public SageOfAncientLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.w.WerewolfOfAncientHunger.class;

        // Sage of Ancient Lore's power and toughness are each equal to the number of cards in your hand.
        DynamicValue xValue = new CardsInControllerHandCount();
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new ConditionalContinuousEffect(new SetPowerToughnessSourceEffect(xValue, Duration.EndOfGame),
                        new TransformedCondition(true), "{this}'s power and toughness are each equal to the total number of cards in your hand")));

        // When Sage of Ancient Lore enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Sage of Ancient Lore.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public SageOfAncientLore(final SageOfAncientLore card) {
        super(card);
    }

    @Override
    public SageOfAncientLore copy() {
        return new SageOfAncientLore(this);
    }
}
