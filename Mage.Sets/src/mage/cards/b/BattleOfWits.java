
package mage.cards.b;

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
import mage.players.Player;

/**
 *
 * @author North
 */
public final class BattleOfWits extends CardImpl {

    public BattleOfWits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");


        // At the beginning of your upkeep, if you have 200 or more cards in your library, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new BattleOfWitsCondition(), "At the beginning of your upkeep, if you have 200 or more cards in your library, you win the game."));
    }

    private BattleOfWits(final BattleOfWits card) {
        super(card);
    }

    @Override
    public BattleOfWits copy() {
        return new BattleOfWits(this);
    }
}

class BattleOfWitsCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() >= 200) {
            return true;
        }
        return false;
    }
}
