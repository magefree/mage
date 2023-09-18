package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImperiousMindbreaker extends CardImpl {

    public ImperiousMindbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Imperious Mindbreaker is paired with another creature, each of those creatures has "Whenever this creature attacks, each opponent mills cards equal to its toughness."
        this.addAbility(new SimpleStaticAbility(new GainAbilityPairedEffect(
                new AttacksTriggeredAbility(new MillCardsEachPlayerEffect(
                        SourcePermanentToughnessValue.getInstance(), TargetController.OPPONENT
                ), false, "Whenever this creature attacks, each opponent mills cards equal to its toughness."),
                "As long as {this} is paired with another creature, each of those creatures has " +
                        "\"Whenever this creature attacks, each opponent mills cards equal to its toughness.\""
        )));
    }

    private ImperiousMindbreaker(final ImperiousMindbreaker card) {
        super(card);
    }

    @Override
    public ImperiousMindbreaker copy() {
        return new ImperiousMindbreaker(this);
    }
}
