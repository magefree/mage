package mage.cards.z;

import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;


/**
 *
 * @author @stwalsh4118
 */
public final class ZenithChronicler extends CardImpl {

    public ZenithChronicler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever a player casts their first multicolored spell each turn, each other player draws a card.
        this.addAbility(new ZenithChroniclerTriggeredAbility(), new SpellsCastWatcher());

    }

    private ZenithChronicler(final ZenithChronicler card) {
        super(card);
    }

    @Override
    public ZenithChronicler copy() {
        return new ZenithChronicler(this);
    }
}

class ZenithChroniclerTriggeredAbility extends TriggeredAbilityImpl {

    public ZenithChroniclerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ZenithChroniclerEffect());
    }

    private ZenithChroniclerTriggeredAbility(final ZenithChroniclerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZenithChroniclerTriggeredAbility copy() {
        return new ZenithChroniclerTriggeredAbility(this);
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
        if (controller != null && spell != null && watcher != null) {
            int multicoloredSpell = 0;
            for (Spell spellCastThisTurn : watcher.getSpellsCastThisTurn(spell.getControllerId())) {
                if (spellCastThisTurn.getColor(game).isMulticolored() && ++multicoloredSpell > 1) {
                    break;
                }
            }
            if (multicoloredSpell == 1) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(spell.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts their first multicolored spell each turn, each other player draws a card.";
    }
}

class ZenithChroniclerEffect extends OneShotEffect {

    public ZenithChroniclerEffect() {
        super(Outcome.DrawCard);
    }

    private ZenithChroniclerEffect(final ZenithChroniclerEffect effect) {
        super(effect);
    }

    @Override
    public ZenithChroniclerEffect copy() {
        return new ZenithChroniclerEffect(this);
    }

    @Override
    public boolean apply (Game game, Ability source) {
        Player controller = game.getPlayer(getTargetPointer().getFirst(game, source));
        Set<UUID> opponents = game.getOpponents(controller.getId());

        for(UUID opponentId : opponents) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.drawCards(1, source, game);
            }
        }

        return true;
    }
}
