package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoanShark extends CardImpl {

    public LoanShark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Loan Shark enters the battlefield, if you've cast two or more spells this turn, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                LoanSharkCondition.instance, "When {this} enters the battlefield, " +
                "if you've cast two or more spells this turn, draw a card."
        ).addHint(StormAbility.getHint()));

        // Plot {3}{U}
        this.addAbility(new PlotAbility("{3}{U}"));
    }

    private LoanShark(final LoanShark card) {
        super(card);
    }

    @Override
    public LoanShark copy() {
        return new LoanShark(this);
    }
}

enum LoanSharkCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getCount(source.getControllerId()) >= 2;
    }
}