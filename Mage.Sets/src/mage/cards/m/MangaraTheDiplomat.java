package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MangaraTheDiplomat extends CardImpl {

    public MangaraTheDiplomat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever an opponent attacks with creatures, if two or more of those creatures are attacking you and/or a planeswalker you control, draw a card.
        this.addAbility(new MangaraTheDiplomatAttackTriggeredAbility());

        // Whenever an opponent casts their second spell each turn, draw a card.
        this.addAbility(new MangaraTheDiplomatCastTriggeredAbility());
    }

    private MangaraTheDiplomat(final MangaraTheDiplomat card) {
        super(card);
    }

    @Override
    public MangaraTheDiplomat copy() {
        return new MangaraTheDiplomat(this);
    }
}

class MangaraTheDiplomatAttackTriggeredAbility extends TriggeredAbilityImpl {

    MangaraTheDiplomatAttackTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private MangaraTheDiplomatAttackTriggeredAbility(final MangaraTheDiplomatAttackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        return player != null
                && player.hasOpponent(game.getActivePlayerId(), game)
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                .filter(getControllerId()::equals)
                .count() >= 2;
    }

    @Override
    public MangaraTheDiplomatAttackTriggeredAbility copy() {
        return new MangaraTheDiplomatAttackTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks with creatures, " +
                "if two or more of those creatures are attacking you " +
                "and/or planeswalkers you control, draw a card.";
    }
}


class MangaraTheDiplomatCastTriggeredAbility extends TriggeredAbilityImpl {

    MangaraTheDiplomatCastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private MangaraTheDiplomatCastTriggeredAbility(final MangaraTheDiplomatCastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return player != null
                && watcher != null
                && player.hasOpponent(event.getPlayerId(), game)
                && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
    }

    @Override
    public MangaraTheDiplomatCastTriggeredAbility copy() {
        return new MangaraTheDiplomatCastTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their second spell each turn, draw a card.";
    }
}
