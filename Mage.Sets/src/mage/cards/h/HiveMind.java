
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class HiveMind extends CardImpl {

    public HiveMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");

        // Whenever a player casts an instant or sorcery spell, each other player copies that spell. Each of those players may choose new targets for their copy.
        this.addAbility(new HiveMindTriggeredAbility());
    }

    private HiveMind(final HiveMind card) {
        super(card);
    }

    @Override
    public HiveMind copy() {
        return new HiveMind(this);
    }
}

class HiveMindTriggeredAbility extends TriggeredAbilityImpl {

    public HiveMindTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HiveMindEffect());
        setTriggerPhrase("Whenever a player casts an instant or sorcery spell, ");
    }

    public HiveMindTriggeredAbility(final HiveMindTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HiveMindTriggeredAbility copy() {
        return new HiveMindTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && (spell.isInstant(game)
                || spell.isSorcery(game))) {
            for (Effect effect : getEffects()) {
                if (effect instanceof HiveMindEffect) {
                    effect.setTargetPointer(new FixedTarget(spell.getId()));
                }
            }
            return true;
        }
        return false;
    }
}

class HiveMindEffect extends OneShotEffect {

    public HiveMindEffect() {
        super(Outcome.Benefit);
        this.staticText = "each other player copies that spell. Each of those players may choose new targets for their copy";
    }

    public HiveMindEffect(final HiveMindEffect effect) {
        super(effect);
    }

    @Override
    public HiveMindEffect copy() {
        return new HiveMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (spell != null && player != null) {
            PlayerList range = game.getState().getPlayersInRange(player.getId(), game);
            for (UUID playerId : game.getState().getPlayerList(spell.getControllerId())) {
                if (!playerId.equals(spell.getControllerId()) && range.contains(playerId)) {
                    spell.createCopyOnStack(game, source, playerId, true);
                }
            }
            return true;
        }
        return false;
    }
}
