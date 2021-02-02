
package mage.cards.n;

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
 * @author jeffwadsworth
 */
public final class NearDeathExperience extends CardImpl {

    public NearDeathExperience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}{W}");


        // At the beginning of your upkeep, if you have exactly 1 life, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new OneLifeCondition(), "At the beginning of your upkeep, if you have exactly 1 life, you win the game."));
    }

    private NearDeathExperience(final NearDeathExperience card) {
        super(card);
    }

    @Override
    public NearDeathExperience copy() {
        return new NearDeathExperience(this);
    }
}

class OneLifeCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getPlayer(source.getControllerId()).getLife() == 1;
    }
}
