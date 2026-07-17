package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.condition.common.NotTransformedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInAllHandsCount;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SageOfAncientLore extends TransformingDoubleFacedCard {

    public SageOfAncientLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF}, "{4}{G}",
                "Werewolf of Ancient Hunger",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Sage of Ancient Lore
        this.getLeftHalfCard().setPT(0, 0);

        // Sage of Ancient Lore's power and toughness are each equal to the number of cards in your hand.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalContinuousEffect(
                        new SetBasePowerToughnessSourceEffect(CardsInControllerHandCount.ANY), NotTransformedCondition.instance,
                        "{this}'s power and toughness are each equal to the total number of cards in your hand"
                )
        ));

        // When Sage of Ancient Lore enters the battlefield, draw a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Sage of Ancient Lore.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Werewolf of Ancient Hunger
        this.getRightHalfCard().setPT(0, 0);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Werewolf of Ancient Hunger's power and toughness are each equal to the total number of cards in all players' hands.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalContinuousEffect(
                        new SetBasePowerToughnessSourceEffect(CardsInAllHandsCount.instance), NotTransformedCondition.instance,
                        "{this}'s power and toughness are each equal to the total number of cards in all players' hands"
                )
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf of Ancient Hunger.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private SageOfAncientLore(final SageOfAncientLore card) {
        super(card);
    }

    @Override
    public SageOfAncientLore copy() {
        return new SageOfAncientLore(this);
    }
}
