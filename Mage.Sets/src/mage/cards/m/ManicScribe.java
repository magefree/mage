package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ManicScribe extends CardImpl {

    public ManicScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Manic Scribe enters the battlefield, each opponent puts the top three cards of their library into their graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(3, TargetController.OPPONENT), false
        ));

        // <i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard,
        // that player puts the top three cards of their library into their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT, new MillCardsTargetEffect(3), false
        ).withInterveningIf(DeliriumCondition.instance).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private ManicScribe(final ManicScribe card) {
        super(card);
    }

    @Override
    public ManicScribe copy() {
        return new ManicScribe(this);
    }
}
