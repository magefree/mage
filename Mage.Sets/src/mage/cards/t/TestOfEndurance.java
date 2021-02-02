
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;

/**
 *
 * @author fireshoes
 */
public final class TestOfEndurance extends CardImpl {

    public TestOfEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // At the beginning of your upkeep, if you have 50 or more life, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new FiftyOrMoreLifeCondition(), "At the beginning of your upkeep, if you have 50 or more life, you win the game."));
    }

    private TestOfEndurance(final TestOfEndurance card) {
        super(card);
    }

    @Override
    public TestOfEndurance copy() {
        return new TestOfEndurance(this);
    }
}


class FiftyOrMoreLifeCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).getLife() >= 50;
    }
}
