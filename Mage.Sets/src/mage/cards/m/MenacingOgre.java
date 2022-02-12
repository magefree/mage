package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MenacingOgre extends CardImpl {

    public MenacingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Menacing Ogre enters the battlefield, each player secretly chooses a number. Then those numbers are revealed. Each player with the highest number loses that much life. If you are one of those players, put two +1/+1 counters on Menacing Ogre.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MenacingOgreEffect(), false));

    }

    private MenacingOgre(final MenacingOgre card) {
        super(card);
    }

    @Override
    public MenacingOgre copy() {
        return new MenacingOgre(this);
    }
}

class MenacingOgreEffect extends OneShotEffect {

    public MenacingOgreEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player secretly chooses a number. Then those numbers are revealed. Each player with the highest number loses that much life. If you are one of those players, put two +1/+1 counters on {this}";
    }

    public MenacingOgreEffect(final MenacingOgreEffect effect) {
        super(effect);
    }

    @Override
    public MenacingOgreEffect copy() {
        return new MenacingOgreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int highestNumber = 0;
        int number = 0;
        Permanent menacingOgre = game.getPermanent(source.getSourceId());
        String message = "Choose a number.";
        Map<Player, Integer> numberChosen = new HashMap<>();

        //players choose numbers
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                number = player.getAmount(0, 1000, message, game);
                numberChosen.put(player, number);
            }
        }
        //get highest number
        for (Player player : numberChosen.keySet()) {
            if (highestNumber < numberChosen.get(player)) {
                highestNumber = numberChosen.get(player);
            }
        }
        //reveal numbers to players and follow through with effect
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                game.informPlayers(player.getLogName() + " chose number " + numberChosen.get(player));
                if (numberChosen.get(player) >= highestNumber) {
                    player.loseLife(highestNumber, game, source, false);
                    if (player.getId().equals(source.getControllerId())
                            && menacingOgre != null) {
                        menacingOgre.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                    }
                }
            }
        }
        return true;
    }
}
