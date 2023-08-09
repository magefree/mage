
package mage.cards.w;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * @author magenoxx_at_gmail.com
 */
public final class WorldAtWar extends CardImpl {

    public WorldAtWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn.
        this.getSpellAbility().addEffect(new WorldAtWarEffect());

        // Rebound (If you cast this spell from your hand, exile it as it resolves. At the beginning of your next upkeep, you may cast this card from exile without paying its mana cost.)
        this.addAbility(new ReboundAbility(), new AttackedThisTurnWatcher());
    }

    private WorldAtWar(final WorldAtWar card) {
        super(card);
    }

    @Override
    public WorldAtWar copy() {
        return new WorldAtWar(this);
    }
}

class WorldAtWarEffect extends OneShotEffect {

    public WorldAtWarEffect() {
        super(Outcome.Benefit);
        staticText = "After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn";
    }

    public WorldAtWarEffect(final WorldAtWarEffect effect) {
        super(effect);
    }

    @Override
    public WorldAtWarEffect copy() {
        return new WorldAtWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // we can't add two turn modes at once, will add additional post combat on delayed trigger resolution
        TurnMod combat = new TurnMod(source.getControllerId()).withExtraPhase(TurnPhase.COMBAT, TurnPhase.POSTCOMBAT_MAIN);
        game.getState().getTurnMods().add(combat);
        UntapDelayedTriggeredAbility delayedTriggeredAbility = new UntapDelayedTriggeredAbility();
        delayedTriggeredAbility.setConnectedTurnMod(combat.getId());
        game.addDelayedTriggeredAbility(delayedTriggeredAbility, source);
        return true;
    }

}

class UntapDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID connectedTurnMod;
    private boolean enabled;

    public UntapDelayedTriggeredAbility() {
        super(new UntapAttackingThisTurnEffect());
    }

    public UntapDelayedTriggeredAbility(UntapDelayedTriggeredAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
        this.enabled = ability.enabled;
    }

    @Override
    public UntapDelayedTriggeredAbility copy() {
        return new UntapDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_CHANGED || event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PHASE_CHANGED && this.connectedTurnMod.equals(event.getSourceId())) {
            enabled = true;
            return false;
        }
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE && enabled) {
            // add additional post combat phase after that
            game.getState().getTurnMods().add(new TurnMod(getControllerId()).withExtraPhase(TurnPhase.POSTCOMBAT_MAIN, TurnPhase.COMBAT));
            enabled = false;
            return true;
        }
        return false;
    }

    public void setConnectedTurnMod(UUID connectedTurnMod) {
        this.connectedTurnMod = connectedTurnMod;
    }

    @Override
    public String getRule() {
        return "At the beginning of that combat, untap all creatures that attacked this turn";
    }
}

class UntapAttackingThisTurnEffect extends OneShotEffect {

    public UntapAttackingThisTurnEffect() {
        super(Outcome.Benefit);
    }

    public UntapAttackingThisTurnEffect(final UntapAttackingThisTurnEffect effect) {
        super(effect);
    }

    @Override
    public UntapAttackingThisTurnEffect copy() {
        return new UntapAttackingThisTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher instanceof AttackedThisTurnWatcher) {
            Set<MageObjectReference> attackedThisTurn = ((AttackedThisTurnWatcher) watcher).getAttackedThisTurnCreatures();
            for (MageObjectReference mor : attackedThisTurn) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null && permanent.isCreature(game)) {
                    permanent.untap(game);
                }
            }
        }
        return true;
    }

}
