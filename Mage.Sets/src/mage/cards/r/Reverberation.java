package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Reverberation extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("sorcery spell");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public Reverberation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // All damage that would be dealt this turn by target sorcery spell is dealt to that spell’s controller instead.
        this.getSpellAbility().addEffect(new ReverberationEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private Reverberation(final Reverberation card) {
        super(card);
    }

    @Override
    public Reverberation copy() {
        return new Reverberation(this);
    }
}

class ReverberationEffect extends ReplacementEffectImpl {

    public ReverberationEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt this turn by target sorcery spell is dealt to that spell's controller instead";
    }

    private ReverberationEffect(final ReverberationEffect effect) {
        super(effect);
    }

    @Override
    public ReverberationEffect copy() {
        return new ReverberationEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
            if (targetSpell != null) {
                Player targetsController = game.getPlayer(targetSpell.getControllerId());
                if (targetsController != null) {
                    targetsController.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if (targetSpell != null) {
            return damageEvent.getAmount() > 0;
        }
        return false;
    }
}
