package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author weirddan455
 */
public final class EsperSentinel extends CardImpl {

    public EsperSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts their first noncreature spell each turn, draw a card unless that player pays {X}, where X is Esper Sentinel's power.
        this.addAbility(new EsperSentinelTriggeredAbility());
    }

    private EsperSentinel(final EsperSentinel card) {
        super(card);
    }

    @Override
    public EsperSentinel copy() {
        return new EsperSentinel(this);
    }
}

class EsperSentinelTriggeredAbility extends TriggeredAbilityImpl {

    public EsperSentinelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EsperSentinelEffect());
    }

    private EsperSentinelTriggeredAbility(final EsperSentinelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EsperSentinelTriggeredAbility copy() {
        return new EsperSentinelTriggeredAbility(this);
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
        return "Whenever an opponent casts their first noncreature spell each turn, draw a card unless that player pays {X}, where X is Esper Sentinel's power.";
    }
}

class EsperSentinelEffect extends OneShotEffect {

    public EsperSentinelEffect() {
        super(Outcome.DrawCard);
    }

    private EsperSentinelEffect(final EsperSentinelEffect effect) {
        super(effect);
    }

    @Override
    public EsperSentinelEffect copy() {
        return new EsperSentinelEffect(this);
    }

    @Override
    public boolean apply (Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && opponent != null && permanent != null) {
            Cost cost = ManaUtil.createManaCost(permanent.getPower().getValue(), false);
            if (!opponent.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "?", source, game)
                    || !cost.pay(source, game, source, opponent.getId(), false)) {
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
