
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class EagerConstruct extends CardImpl {

    public EagerConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Eager Construct enters the battlefield, each player may scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EagerConstructEffect()));
    }

    private EagerConstruct(final EagerConstruct card) {
        super(card);
    }

    @Override
    public EagerConstruct copy() {
        return new EagerConstruct(this);
    }
}

class EagerConstructEffect extends OneShotEffect {

    public EagerConstructEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may scry 1";
    }

    private EagerConstructEffect(final EagerConstructEffect effect) {
        super(effect);
    }

    @Override
    public EagerConstructEffect copy() {
        return new EagerConstructEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(outcome, "Scry 1? <i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>", source, game)) {
                        player.scry(1, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
