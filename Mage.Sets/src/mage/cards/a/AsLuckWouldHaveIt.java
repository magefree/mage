package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.RollDieType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class AsLuckWouldHaveIt extends CardImpl {

    static final String rule = "put a number of luck counters on {this} equal to the result. Then if there are 100 or more luck counters on {this}, you win the game.";

    public AsLuckWouldHaveIt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever you roll a die, put a number of luck counters on As Luck Would Have It equal to the result. Then if there are 100 or more luck counters on As Luck Would Have It, you win the game.
        this.addAbility(new AsLuckWouldHaveItTriggeredAbility());
    }

    private AsLuckWouldHaveIt(final AsLuckWouldHaveIt card) {
        super(card);
    }

    @Override
    public AsLuckWouldHaveIt copy() {
        return new AsLuckWouldHaveIt(this);
    }
}

class AsLuckWouldHaveItTriggeredAbility extends TriggeredAbilityImpl {

    public AsLuckWouldHaveItTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AsLuckWouldHaveItEffect(), false);
        setTriggerPhrase("Whenever you roll a die, ");
    }

    public AsLuckWouldHaveItTriggeredAbility(final AsLuckWouldHaveItTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AsLuckWouldHaveItTriggeredAbility copy() {
        return new AsLuckWouldHaveItTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        // Any die roll with a numerical result will add luck counters to As Luck Would Have It.
        // Rolling the planar die will not cause the second ability to trigger.
        // (2018-01-19)
        if (this.isControlledBy(event.getPlayerId()) && drEvent.getRollDieType() == RollDieType.NUMERICAL) {
            // silver border card must look for "result" instead "natural result"
            this.getEffects().setValue("rolled", drEvent.getResult());
            return true;
        }
        return false;
    }
}

class AsLuckWouldHaveItEffect extends OneShotEffect {

    public AsLuckWouldHaveItEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a number of luck counters on {this} equal to the result. Then if there are 100 or more luck counters on {this}, you win the game.";
    }

    public AsLuckWouldHaveItEffect(final AsLuckWouldHaveItEffect effect) {
        super(effect);
    }

    @Override
    public AsLuckWouldHaveItEffect copy() {
        return new AsLuckWouldHaveItEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (getValue("rolled") != null) {
                int amount = (Integer) getValue("rolled");
                permanent.addCounters(new Counter(CounterType.LUCK.getName(), amount), source.getControllerId(), source, game);

                if (permanent.getCounters(game).getCount(CounterType.LUCK) >= 100) {
                    Player player = game.getPlayer(permanent.getControllerId());
                    if (player != null) {
                        player.won(game);
                    }
                }

                return true;
            }
        }
        return false;

    }
}
