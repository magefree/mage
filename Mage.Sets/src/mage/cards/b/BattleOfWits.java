package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author North
 */
public final class BattleOfWits extends CardImpl {

    public BattleOfWits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // At the beginning of your upkeep, if you have 200 or more cards in your library, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(BattleOfWitsCondition.instance));
    }

    private BattleOfWits(final BattleOfWits card) {
        super(card);
    }

    @Override
    public BattleOfWits copy() {
        return new BattleOfWits(this);
    }
}

enum BattleOfWitsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getLibrary)
                .map(Library::size)
                .orElse(0) >= 200;
    }

    @Override
    public String toString() {
        return "you have 200 or more cards in your library";
    }
}
