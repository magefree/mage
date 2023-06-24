package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class TenuousTruce extends CardImpl {

    public TenuousTruce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.addSubType(SubType.AURA);

        // Enchant opponent
        TargetPlayer auraTarget = new TargetOpponent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DrawCard));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of enchanted opponent’s end step, you and that player each draw a card.
        Ability drawAbility = new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you "),
                TargetController.ENCHANTED,
                false);
        Effect enchantedPlayerDrawEffect = new DrawCardTargetEffect(1);
        enchantedPlayerDrawEffect.concatBy("and").setText("that player each draw a card");
        drawAbility.addEffect(enchantedPlayerDrawEffect);
        this.addAbility(drawAbility);

        // When you attack enchanted opponent or a planeswalker they control
        // or when they attack you or a planeswalker you control,
        // sacrifice Tenuous Truce.
        this.addAbility(new TenuousTruceAttackTriggeredAbility());
    }

    private TenuousTruce(final TenuousTruce card) {
        super(card);
    }

    @Override
    public TenuousTruce copy() {
        return new TenuousTruce(this);
    }
}

class TenuousTruceAttackTriggeredAbility extends TriggeredAbilityImpl {

    TenuousTruceAttackTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
        setTriggerPhrase("When you attack enchanted opponent or a planeswalker they control " +
                "or when they attack you or a planeswalker you control, ");
    }

    TenuousTruceAttackTriggeredAbility(final TenuousTruceAttackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent tenuousTruce = game.getPermanent(this.getSourceId());
        Player controller = game.getPlayer(this.getControllerId());
        Player attacker = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (tenuousTruce == null || controller == null || attacker == null) {
            return false;
        }

        Player enchantedPlayer = game.getPlayer(tenuousTruce.getAttachedTo());
        if (enchantedPlayer == null) {
            return false;
        }

        Set<UUID> defenderIds = game.getCombat().getPlayerDefenders(game, true);
        if (controller.equals(attacker)) {
            return TenuousTruceAttackTriggeredAbility.playerOneAttackingPlayerBOrTheirPlaneswalker(controller.getId(), enchantedPlayer.getId(), defenderIds, game);
        } else if (enchantedPlayer.equals(attacker)) {
            return TenuousTruceAttackTriggeredAbility.playerOneAttackingPlayerBOrTheirPlaneswalker(enchantedPlayer.getId(), controller.getId(), defenderIds, game);
        } else {
            return false;
        }
    }

    private static boolean playerOneAttackingPlayerBOrTheirPlaneswalker(UUID playerAId, UUID playerBId, Set<UUID> defenderIds, Game game) {
        if (defenderIds.contains(playerBId)) {
            return true;
        }
        // Check planeswalkers
        for (UUID defenderId : defenderIds) {
            Permanent perm = game.getPermanent(defenderId);
            if (perm != null && perm.getOwnerId().equals(playerBId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TenuousTruceAttackTriggeredAbility copy() {
        return new TenuousTruceAttackTriggeredAbility(this);
    }
}
