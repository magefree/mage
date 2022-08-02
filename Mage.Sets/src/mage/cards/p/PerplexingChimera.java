           
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * You may exchange control of Perplexing Chimera and any spell cast by an
 * opponent, not just one with targets.
 *
 * You make the decision whether to exchange control of Perplexing Chimera and
 * the spell as the triggered ability resolves.
 *
 * If Perplexing Chimera leaves the battlefield or the spell leaves the stack
 * before the triggered ability resolves, you can't make the exchange.
 *
 * Neither Perplexing Chimera nor the spell changes zones. Only control of them
 * is exchanged.
 *
 * After the ability resolves, you control the spell. Any instance of "you" in
 * that spell's text now refers to you, "an opponent" refers to one of your
 * opponents, and so on. The change of control happens before new targets are
 * chosen, so any targeting restrictions such as "target opponent" or "target
 * creature you control" are now made in reference to you, not the spell's
 * original controller. You may change those targets to be legal in reference to
 * you, or, if those are the spell's only targets, the spell will be countered
 * on resolution for having illegal targets. When the spell resolves, any
 * illegal targets are unaffected by it and you make all decisions the spell's
 * effect calls for.
 *
 * You may change any of the spell's targets. If you change a target, you must
 * choose a legal target for the spell. If you can't, you must leave the target
 * the same (even if that target is now illegal).
 *
 * Gaining control of a spell and changing its targets won't cause any heroic
 * abilities of the new targets to trigger.
 *
 * If you gain control of an instant or sorcery spell, it will be put into its
 * owner's graveyard as it resolves or is countered.
 *
 * In some unusual cases, you may not control Perplexing Chimera when its
 * triggered ability resolves (perhaps because the triggered ability triggered
 * again and resolved while the original ability was on the stack). In these
 * cases, you can exchange control of Perplexing Chimera and the spell that
 * causes the ability to trigger, even if you control neither of them. If you
 * do, you'll be able to change targets of the spell, not the spell's new
 * controller.
 *
 *
 * @author LevelX2
 */
public final class PerplexingChimera extends CardImpl {

    public PerplexingChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent casts a spell, you may exchange control of Perplexing Chimera and that spell. If you do, you may choose new targets for the spell.
        this.addAbility(new PerplexingChimeraTriggeredAbility());
    }

    private PerplexingChimera(final PerplexingChimera card) {
        super(card);
    }

    @Override
    public PerplexingChimera copy() {
        return new PerplexingChimera(this);
    }
}

class PerplexingChimeraTriggeredAbility extends TriggeredAbilityImpl {

    public PerplexingChimeraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PerplexingChimeraControlExchangeEffect(), true);
        setTriggerPhrase("Whenever an opponent casts a spell, ");
    }

    public PerplexingChimeraTriggeredAbility(final PerplexingChimeraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public PerplexingChimeraTriggeredAbility copy() {
        return new PerplexingChimeraTriggeredAbility(this);
    }
}

class PerplexingChimeraControlExchangeEffect extends OneShotEffect {

    public PerplexingChimeraControlExchangeEffect() {
        super(Outcome.Benefit);
        this.staticText = "exchange control of {this} and that spell. If you do, you may choose new targets for the spell";
    }

    public PerplexingChimeraControlExchangeEffect(final PerplexingChimeraControlExchangeEffect effect) {
        super(effect);
    }

    @Override
    public PerplexingChimeraControlExchangeEffect copy() {
        return new PerplexingChimeraControlExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (spell != null && controller != null) {
            Player spellCaster = game.getPlayer(spell.getControllerId());
            // controller gets controll of spell
            spell.setControllerId(controller.getId());
            // and chooses new targets
            spell.chooseNewTargets(game, controller.getId());
            game.informPlayers(controller.getLogName() + " got control of " + spell.getName() + " spell.");
            // and spell controller get control of Perplexing Chimera
            if (spellCaster != null) {
                ContinuousEffect effect = new PerplexingChimeraControlEffect();
                effect.setTargetPointer(new FixedTarget(spellCaster.getId()));
                game.addEffect(effect, source);
            }
        }

        return false;
    }
}

class PerplexingChimeraControlEffect extends ContinuousEffectImpl {

    public PerplexingChimeraControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "PerplexingChimeraControlEffect";
    }

    public PerplexingChimeraControlEffect(final PerplexingChimeraControlEffect effect) {
        super(effect);
    }

    @Override
    public PerplexingChimeraControlEffect copy() {
        return new PerplexingChimeraControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.changeControllerId(this.getTargetPointer().getFirst(game, source), game, source);
        } else {
            discard(); // if card once left the battlefield the effect can be discarded
        }
        return true;
    }

}
