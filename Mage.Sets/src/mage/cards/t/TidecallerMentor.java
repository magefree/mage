package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TidecallerMentor extends CardImpl {

    public TidecallerMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Threshold -- When Tidecaller Mentor enters, if seven or more cards are in your graveyard, return up to one target nonland permanent to its owner's hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()),
                ThresholdCondition.instance, "When {this} enters, if seven or more cards " +
                "are in your graveyard, return up to one target nonland permanent to its owner's hand."
        );
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        this.addAbility(ability.setAbilityWord(AbilityWord.THRESHOLD));
    }

    private TidecallerMentor(final TidecallerMentor card) {
        super(card);
    }

    @Override
    public TidecallerMentor copy() {
        return new TidecallerMentor(this);
    }
}
