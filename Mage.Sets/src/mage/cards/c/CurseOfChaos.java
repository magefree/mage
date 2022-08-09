package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CurseOfChaos extends CardImpl {

    public CurseOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DrawCard));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever a player attacks enchanted player with one or more creatures, that attacking player may discard a card. If the player does, they draw a card.
        this.addAbility(new CurseOfChaosTriggeredAbility());
    }

    private CurseOfChaos(final CurseOfChaos card) {
        super(card);
    }

    @Override
    public CurseOfChaos copy() {
        return new CurseOfChaos(this);
    }
}

class CurseOfChaosTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfChaosTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfChaosEffect(), false); // false because handled in effect
        setTriggerPhrase("Whenever a player attacks enchanted player with one or more creatures, ");
    }

    public CurseOfChaosTriggeredAbility(final CurseOfChaosTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getCombat().getPlayerDefenders(game, false).contains(enchantment.getAttachedTo())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackingPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public CurseOfChaosTriggeredAbility copy() {
        return new CurseOfChaosTriggeredAbility(this);
    }

}

class CurseOfChaosEffect extends OneShotEffect {

    public CurseOfChaosEffect() {
        super(Outcome.Benefit);
        this.staticText = "that attacking player may discard a card. If the player does, they draw a card";
    }

    public CurseOfChaosEffect(final CurseOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfChaosEffect copy() {
        return new CurseOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player attacker = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (attacker != null) {
            if (!attacker.getHand().isEmpty() && attacker.chooseUse(outcome, "Discard a card and draw a card?", source, game)) {
                // TODO: This should check that a card was actually discarded
                attacker.discard(1, false, false, source, game);
                attacker.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
