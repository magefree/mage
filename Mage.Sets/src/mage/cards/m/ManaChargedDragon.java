
package mage.cards.m;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class ManaChargedDragon extends CardImpl {

    public ManaChargedDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Join forces - Whenever Mana-Charged Dragon attacks or blocks, each player starting with you may pay any amount of mana. Mana-Charged Dragon gets +X/+0 until end of turn, where X is the total amount of mana paid this way.
        this.addAbility(new ManaChargedDragonTriggeredAbility());
    }

    public ManaChargedDragon(final ManaChargedDragon card) {
        super(card);
    }

    @Override
    public ManaChargedDragon copy() {
        return new ManaChargedDragon(this);
    }
}

class ManaChargedDragonTriggeredAbility extends AttacksOrBlocksTriggeredAbility {
    
    ManaChargedDragonTriggeredAbility() {
        super(new ManaChargedDragonEffect(), false);
    }
    
    @Override
    public String getRule() {
        return "Join forces &mdash; Whenever {this} attacks or blocks, each player starting with you may pay any amount of mana. {this} gets +X/+0 until end of turn, where X is the total amount of mana paid this way";
    }
}

class ManaChargedDragonEffect extends OneShotEffect {
    
    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (player.canRespond() && !payed) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            } else {
                payed = true;
            }
        }
        game.informPlayers(new StringBuilder(player.getLogName()).append(" pays {").append(xValue).append("}.").toString());
        return xValue;
    }
    
    ManaChargedDragonEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "each player starting with you may pay any amount of mana. {this} gets +X/+0 until end of turn, where X is the total amount of mana paid this way";
    }
    
    ManaChargedDragonEffect(final ManaChargedDragonEffect effect) {
        super(effect);
    }
    
    @Override
    public ManaChargedDragonEffect copy() {
        return new ManaChargedDragonEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += playerPaysXGenericMana(controller, source, game);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += playerPaysXGenericMana(player, source, game);

                    }
                }
            }
            ContinuousEffect effect = new BoostSourceEffect(xSum, 0, Duration.EndOfTurn);
            game.addEffect(effect, source);
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
