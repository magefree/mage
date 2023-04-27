package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import mage.watchers.common.SpellsCastWatcher;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ShadowInTheWarp extends CardImpl {

    private static final FilterCreatureCard filterCost = new FilterCreatureCard("The first creature spell");

    static {
        filterCost.add(new FirstCastCreatureSpellPredicate());
    }

    public ShadowInTheWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");

        // The first creature spell you cast each turn costs {2} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filterCost, 2);
        effect.setText("The first creature spell you cast each turn costs {2} less to cast.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect), new ShadowInTheWarpWatcher());

        // Whenever an opponent casts their first noncreature spell each turn, Shadow in the Warp deals 2 damage to that player.
        this.addAbility(new ShadowInTheWarpTriggeredAbility(), new SpellsCastWatcher());
    }

    private ShadowInTheWarp(final ShadowInTheWarp card) {
        super(card);
    }

    @Override
    public ShadowInTheWarp copy() {
        return new ShadowInTheWarp(this);
    }
}

class ShadowInTheWarpWatcher extends Watcher {

    private final Map<UUID, Integer> playerCreatureSpells;

    public ShadowInTheWarpWatcher() {
        super(WatcherScope.GAME);
        playerCreatureSpells = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature(game)) {
                playerCreatureSpells.put(event.getPlayerId(), creatureSpellsCastThisTurn(event.getPlayerId()) + 1);
            }
        }
    }

    public int creatureSpellsCastThisTurn(UUID playerId) {
        return playerCreatureSpells.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        playerCreatureSpells.clear();
    }
}

class FirstCastCreatureSpellPredicate implements ObjectSourcePlayerPredicate<Controllable> {

    @Override
    public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
        if (input.getObject() instanceof Card
                && ((Card) input.getObject()).isCreature(game)) {
            ShadowInTheWarpWatcher watcher = game.getState().getWatcher(ShadowInTheWarpWatcher.class);
            return watcher != null && watcher.creatureSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first creature spell you cast each turn";
    }
}

class ShadowInTheWarpTriggeredAbility extends TriggeredAbilityImpl {

    public ShadowInTheWarpTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2, false));
    }

    private ShadowInTheWarpTriggeredAbility(final ShadowInTheWarpTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShadowInTheWarpTriggeredAbility copy() {
        return new ShadowInTheWarpTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        Spell spell = game.getSpell(event.getTargetId());
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (controller != null && spell != null && watcher != null && !spell.isCreature(game) && controller.hasOpponent(spell.getControllerId(), game)) {
            int nonCreatureSpells = 0;
            for (Spell spellCastThisTurn : watcher.getSpellsCastThisTurn(spell.getControllerId())) {
                if (!spellCastThisTurn.isCreature(game) && ++nonCreatureSpells > 1) {
                    break;
                }
            }
            if (nonCreatureSpells == 1) {
                getEffects().setTargetPointer(new FixedTarget(spell.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their first noncreature spell each turn, {this} deals 2 damage to that player.";
    }
}
