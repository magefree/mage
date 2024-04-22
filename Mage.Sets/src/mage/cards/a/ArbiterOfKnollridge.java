
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author anonymous
 */
public final class ArbiterOfKnollridge extends CardImpl {

    public ArbiterOfKnollridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // When Arbiter of Knollridge enters the battlefield, each player's life total becomes the highest life total among all players.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArbiterOfKnollridgeEffect()));
    }

    private ArbiterOfKnollridge(final ArbiterOfKnollridge card) {
        super(card);
    }

    @Override
    public ArbiterOfKnollridge copy() {
        return new ArbiterOfKnollridge(this);
    }
}

class ArbiterOfKnollridgeEffect extends OneShotEffect {
    ArbiterOfKnollridgeEffect() {
        super(Outcome.GainLife);
        staticText = "each player's life total becomes the highest life total among all players";
    }

    private ArbiterOfKnollridgeEffect(final ArbiterOfKnollridgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxLife = 0;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                if (maxLife < p.getLife()) {
                    maxLife = p.getLife();
                }
            }
        }
        for (UUID pid : playerList) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                p.setLife(maxLife, game, source);
            }
        }
        return true;
    }

    @Override
    public ArbiterOfKnollridgeEffect copy() {
        return new ArbiterOfKnollridgeEffect(this);
    }
}