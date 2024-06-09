package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 * @author jeffwadsworth
 */
public class BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect extends OneShotEffect {

    private final Token token;
    private final Duration duration;

    public BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(Token token, String text, Duration duration) {
        super(Outcome.BecomeCreature);
        staticText = text;
        this.token = token;
        this.duration = duration;
    }

    protected BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(final BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.duration = effect.duration;
    }

    @Override
    public BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect copy() {
        return new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        Permanent enchanted = game.getPermanent(permanent.getAttachedTo());
        if (enchanted == null) {
            return false;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                token, false, true, duration
        ).setTargetPointer(new FixedTarget(enchanted, game)), source);
        return true;
    }
}
