package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author fireshoes
 */
public final class MindwrackDemon extends CardImpl {

    public MindwrackDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Mindbreaker Demon enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4)));

        // At the beginning of your upkeep, if you don't have 4 or more card types in your graveyard, you lose 4 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(4), TargetController.YOU, false),
                new InvertCondition(DeliriumCondition.instance),
                "<i>Delirium</i> &mdash; At the beginning of your upkeep, you lose 4 life unless there are four or more card types among cards in your graveyard.");
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private MindwrackDemon(final MindwrackDemon card) {
        super(card);
    }

    @Override
    public MindwrackDemon copy() {
        return new MindwrackDemon(this);
    }
}
