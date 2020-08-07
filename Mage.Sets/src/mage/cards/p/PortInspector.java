package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class PortInspector extends CardImpl {

    public PortInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Port Inspector becomes blocked, you may look at defending player's hand.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                Zone.BATTLEFIELD, new LookAtDefendingPlayersHandEffect(), true, true));
    }

    private PortInspector(final PortInspector card) {
        super(card);
    }

    @Override
    public PortInspector copy() {
        return new PortInspector(this);
    }
}

class LookAtDefendingPlayersHandEffect extends OneShotEffect {

    public LookAtDefendingPlayersHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at defending player's hand";
    }

    public LookAtDefendingPlayersHandEffect(final LookAtDefendingPlayersHandEffect effect) {
        super(effect);
    }

    @Override
    public LookAtDefendingPlayersHandEffect copy() {
        return new LookAtDefendingPlayersHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player defendingPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null
                && defendingPlayer != null) {
            controller.lookAtCards(sourceObject != null
                    ? sourceObject.getIdName() : null,
                    defendingPlayer.getHand(), game);
            return true;
        }
        return false;
    }

}
