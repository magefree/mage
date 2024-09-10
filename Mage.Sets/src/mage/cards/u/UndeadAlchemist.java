
package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class UndeadAlchemist extends CardImpl {

    public UndeadAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // If a Zombie you control would deal combat damage to a player, instead that player puts that many cards from the top of their library into their graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UndeadAlchemistEffect()));

        // Whenever a creature card is put into an opponent's graveyard from their library, exile that card and create a 2/2 black Zombie creature token.
        this.addAbility(new UndeadAlchemistTriggeredAbility());
    }

    private UndeadAlchemist(final UndeadAlchemist card) {
        super(card);
    }

    @Override
    public UndeadAlchemist copy() {
        return new UndeadAlchemist(this);
    }
}

class UndeadAlchemistTriggeredAbility extends TriggeredAbilityImpl {

    public UndeadAlchemistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(), false);
        this.addEffect(new CreateTokenEffect(new ZombieToken()));
    }

    private UndeadAlchemistTriggeredAbility(final UndeadAlchemistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UndeadAlchemistTriggeredAbility copy() {
        return new UndeadAlchemistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.LIBRARY && zEvent.getToZone() == Zone.GRAVEYARD && game.getOpponents(this.getControllerId()).contains(zEvent.getPlayerId())) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature(game)) {
                if (game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(card, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature card is put into an opponent's graveyard from their library, exile that card and create a 2/2 black Zombie creature token.";
    }
}

class UndeadAlchemistEffect extends ReplacementEffectImpl {

    UndeadAlchemistEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "If a Zombie you control would deal combat damage to a player, instead that player mills that many cards";
    }

    private UndeadAlchemistEffect(final UndeadAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        if (player != null) {
            player.millCards(event.getAmount(), source, game);
            return true;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        if (damageEvent.isCombatDamage()) {
            UUID controllerId = source.getControllerId();
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.hasSubtype(SubType.ZOMBIE, game) && permanent.isControlledBy(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UndeadAlchemistEffect copy() {
        return new UndeadAlchemistEffect(this);
    }
}
