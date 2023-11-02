package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoatiScavenger extends CardImpl {

    public CoatiScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.RACCOON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Descend 4 -- When Coati Scavenger enters the battlefield, if there are four or more permanent cards in your graveyard, return target permanent card from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()),
                DescendCondition.FOUR, "When {this} enters the battlefield, if there are four or more " +
                "permanent cards in your graveyard, return target permanent card from your graveyard to your hand."
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT));
        this.addAbility(ability.setAbilityWord(AbilityWord.DESCEND_4).addHint(DescendCondition.getHint()));
    }

    private CoatiScavenger(final CoatiScavenger card) {
        super(card);
    }

    @Override
    public CoatiScavenger copy() {
        return new CoatiScavenger(this);
    }
}
