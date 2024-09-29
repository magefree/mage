package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HandThatFeeds extends CardImpl {

    public HandThatFeeds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Delirium -- Whenever Hand That Feeds attacks while there are four or more card types among cards in your graveyard, it gets +2/+0 and gains menace until end of turn.
        Ability ability = new ConditionalTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)),
                DeliriumCondition.instance, "Whenever {this} attacks while there are four or more " +
                "card types among cards in your graveyard, it gets +2/+0 and gains menace until end of turn."
        );
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(false), Duration.EndOfTurn));
        this.addAbility(ability.setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardHint.YOU));
    }

    private HandThatFeeds(final HandThatFeeds card) {
        super(card);
    }

    @Override
    public HandThatFeeds copy() {
        return new HandThatFeeds(this);
    }
}
