package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MangaraTheDiplomat extends CardImpl {

    public MangaraTheDiplomat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever an opponent attacks with creatures, if two or more of those creatures are attacking you and/or a planeswalker you control, draw a card.
        this.addAbility(new MangaraTheDiplomatTriggeredAbility());

        // Whenever an opponent casts their second spell each turn, draw a card.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.OPPONENT
        ));
    }

    private MangaraTheDiplomat(final MangaraTheDiplomat card) {
        super(card);
    }

    @Override
    public MangaraTheDiplomat copy() {
        return new MangaraTheDiplomat(this);
    }
}

class MangaraTheDiplomatTriggeredAbility extends TriggeredAbilityImpl {

    MangaraTheDiplomatTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private MangaraTheDiplomatTriggeredAbility(final MangaraTheDiplomatTriggeredAbility ability) {
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
    public MangaraTheDiplomatTriggeredAbility copy() {
        return new MangaraTheDiplomatTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks with creatures, " +
                "if two or more of those creatures are attacking you " +
                "and/or planeswalkers you control, draw a card.";
    }
}
