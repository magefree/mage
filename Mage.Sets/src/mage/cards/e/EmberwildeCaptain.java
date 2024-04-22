package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberwildeCaptain extends CardImpl {

    public EmberwildeCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Emberwilde Captain enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // Whenever an opponent attacks you while you're the monarch, Emberwilde Captain deals damage to that player equal to the number of cards in their hand.
        this.addAbility(new EmberwildeCaptainTriggeredAbility());
    }

    private EmberwildeCaptain(final EmberwildeCaptain card) {
        super(card);
    }

    @Override
    public EmberwildeCaptain copy() {
        return new EmberwildeCaptain(this);
    }
}

class EmberwildeCaptainTriggeredAbility extends TriggeredAbilityImpl {

    public EmberwildeCaptainTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EmberwildeCaptainEffect(), false);
    }

    private EmberwildeCaptainTriggeredAbility(final EmberwildeCaptainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player == null) {
            return false;
        }

        // controller must be monarch
        if (!player.getId().equals(game.getMonarchId())) {
            return false;
        }

        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null
                    && player.hasOpponent(creature.getControllerId(), game)
                    && player.getId().equals(game.getCombat().getDefendingPlayerId(attacker, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you while you're the monarch, " +
                "{this} deals damage to that player equal to the number of cards in their hand.";
    }

    @Override
    public EmberwildeCaptainTriggeredAbility copy() {
        return new EmberwildeCaptainTriggeredAbility(this);
    }
}

class EmberwildeCaptainEffect extends OneShotEffect {

    EmberwildeCaptainEffect() {
        super(Outcome.Benefit);
    }

    private EmberwildeCaptainEffect(final EmberwildeCaptainEffect effect) {
        super(effect);
    }

    @Override
    public EmberwildeCaptainEffect copy() {
        return new EmberwildeCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null || player.getHand().size() < 1) {
            return false;
        }
        return player.damage(player.getHand().size(), source.getSourceId(), source, game) > 0;
    }
}
