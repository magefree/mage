package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class TheEverflowingWell extends CardImpl {

    public TheEverflowingWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.t.TheMyriadPools.class;
        this.color.setBlue(true);

        // When the Everflowing Well enters the battlefield, mill two cards, then draw two cards.
        Ability entersAbility = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        entersAbility.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.addAbility(entersAbility);

        // Descend 8 -- At the beginning of your upkeep, if there are eight or more permanent cards in your graveyard, transform The Everflowing Well.
        this.addAbility(new TransformAbility());
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, false),
                DescendCondition.EIGHT, "At the beginning of your upkeep, if there are eight or more permanent cards in your graveyard, transform {this}.");
        ability.setAbilityWord(AbilityWord.DESCEND_8).addHint(DescendCondition.getHint());
        this.addAbility(ability);
    }

    private TheEverflowingWell(final TheEverflowingWell card) {
        super(card);
    }

    @Override
    public TheEverflowingWell copy() {
        return new TheEverflowingWell(this);
    }

}
