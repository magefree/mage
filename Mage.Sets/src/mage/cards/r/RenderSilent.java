package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class RenderSilent extends CardImpl {

    public RenderSilent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{U}");

        // Counter target spell. Its controller can't cast spells this turn.
        this.getSpellAbility().addEffect(new RenderSilentCounterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new RenderSilentEffect());
    }

    private RenderSilent(final RenderSilent card) {
        super(card);
    }

    @Override
    public RenderSilent copy() {
        return new RenderSilent(this);
    }
}

class RenderSilentCounterEffect extends OneShotEffect {

    public RenderSilentCounterEffect() {
        super(Outcome.Detriment);
    }

    public RenderSilentCounterEffect(final RenderSilentCounterEffect effect) {
        super(effect);
    }

    @Override
    public RenderSilentCounterEffect copy() {
        return new RenderSilentCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            source.getEffects().get(1).setTargetPointer(new FixedTarget(spell.getControllerId()));
            return game.getStack().counter(source.getFirstTarget(), source, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target " + mode.getTargets().get(0).getTargetName();
    }

}

class RenderSilentEffect extends ContinuousRuleModifyingEffectImpl {

    public RenderSilentEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Its controller can't cast spells this turn";
    }

    public RenderSilentEffect(final RenderSilentEffect effect) {
        super(effect);
    }

    @Override
    public RenderSilentEffect copy() {
        return new RenderSilentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null && player.getId().equals(event.getPlayerId())) {
            return true;
        }
        return false;
    }

}
