package mage.cards.v;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class VaevictisAsmadiTheDire extends CardImpl {

    public VaevictisAsmadiTheDire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Vaevictis Asmadi, the Dire attacks, for each player, choose target permanent that player controls. Those players sacrifice those permanents. Each player who sacrificed a permanent this way reveals the top card of their library, then puts it onto the battlefield if it's a permanent card.
        this.addAbility(new VaevictisAsmadiTheDireTriggeredAbility());
    }

    private VaevictisAsmadiTheDire(final VaevictisAsmadiTheDire card) {
        super(card);
    }

    @Override
    public VaevictisAsmadiTheDire copy() {
        return new VaevictisAsmadiTheDire(this);
    }
}

class VaevictisAsmadiTheDireTriggeredAbility extends TriggeredAbilityImpl {

    public VaevictisAsmadiTheDireTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VaevictisAsmadiTheDireEffect(), false);
    }

    public VaevictisAsmadiTheDireTriggeredAbility(final VaevictisAsmadiTheDireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VaevictisAsmadiTheDireTriggeredAbility copy() {
        return new VaevictisAsmadiTheDireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().getAttackers().contains(this.getSourceId())) {
            return false;
        }
        this.getTargets().clear();
        for (UUID playerId : game.getState().getPlayerList(this.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterPermanent("permanent controlled by " + player.getName());
            filter.add(new ControllerIdPredicate(playerId));
            TargetPermanent target = new TargetPermanent(filter);
            this.addTarget(target);
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, for each player, "
                + "choose target permanent that player controls. "
                + "Those players sacrifice those permanents. "
                + "Each player who sacrificed a permanent this way reveals the top card of their library, "
                + "then puts it onto the battlefield if it's a permanent card";
    }
}

class VaevictisAsmadiTheDireEffect extends OneShotEffect {

    public VaevictisAsmadiTheDireEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "for each player, choose target permanent that player controls. "
                + "Those players sacrifice those permanents. "
                + "Each player who sacrificed a permanent this way reveals the top card of their library, "
                + "then puts it onto the battlefield if it's a permanent card";
    }

    public VaevictisAsmadiTheDireEffect(final VaevictisAsmadiTheDireEffect effect) {
        super(effect);
    }

    @Override
    public VaevictisAsmadiTheDireEffect copy() {
        return new VaevictisAsmadiTheDireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> playersToFlip = new ArrayList<>();
        for (Target target : source.getTargets()) {
            for (UUID permId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permId);
                if (permanent == null
                        || !permanent.sacrifice(source, game)) {
                    continue;
                }
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    playersToFlip.add(player);
                }
            }
        }
        for (Player player : playersToFlip) {
            if (player == null) {
                return false;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            player.revealCards(source, new CardsImpl(card), game);
            if (card.isPermanent(game)) {
                player.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        return true;
    }
}
