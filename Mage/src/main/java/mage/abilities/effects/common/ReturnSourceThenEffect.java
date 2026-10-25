package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.TargetPointer;

/**
 * An effect that puts its own source onto the battlefield and then does more to "it", e.g. "return this card
 * from your graveyard to the battlefield. It gains ...". The additional effects are ordinary effects, source
 * effects included, and they resolve only if the source was actually returned.
 *
 * @author notgreat, Claude Opus 5.5
 */
public abstract class ReturnSourceThenEffect extends OneShotEffect {

    private final Effects additionalEffects;

    protected ReturnSourceThenEffect(Outcome outcome) {
        super(outcome);
        this.additionalEffects = new Effects();
    }

    protected ReturnSourceThenEffect(final ReturnSourceThenEffect effect) {
        super(effect);
        this.additionalEffects = effect.additionalEffects.copy();
    }

    /**
     * Resolves only if the source was returned, with the returned permanent as its source. The text joins on
     * like an ability's next effect, so {@code concatBy} picks the connector.
     */
    public ReturnSourceThenEffect withAdditionalEffect(Effect effect) {
        additionalEffects.add(effect);
        return this;
    }

    /**
     * Call only once the source has actually been returned, with the permanent the move produced. The move is
     * processed first, as between an ability's effects (608.2e), so its enter events see the permanent before
     * the additional effects change it.
     * <p>
     * 400.7j: "it" later in the ability is the returned permanent, a new object. The shared ability keeps
     * referring to the old one; only the additional effects get a copy that follows.
     */
    protected void resolveAdditionalEffects(Game game, Ability source, Permanent returned) {
        if (additionalEffects.isEmpty()) {
            return;
        }
        game.processAction();
        Ability returnedSource = source.copy();
        returnedSource.setSourceId(returned.getId());
        returnedSource.setSourceObjectZoneChangeCounter(returned.getZoneChangeCounter(game));
        returnedSource.setSourcePermanentTransformCount(game);
        additionalEffects.resolve(game, returnedSource);
    }

    @Override
    public void setValue(String key, Object value) {
        super.setValue(key, value);
        additionalEffects.setValue(key, value);
    }

    @Override
    public ReturnSourceThenEffect setTargetPointer(TargetPointer targetPointer) {
        super.setTargetPointer(targetPointer);
        additionalEffects.setTargetPointer(targetPointer);
        return this;
    }

    @Override
    public void newId() {
        super.newId();
        additionalEffects.newId();
    }

    @Override
    public String getText(Mode mode) {
        String text = super.getText(mode);
        if (additionalEffects.isEmpty()) {
            return text;
        }
        Effects effects = new Effects(new InfoEffect(text));
        effects.addAll(additionalEffects);
        return effects.getText(mode);
    }
}
