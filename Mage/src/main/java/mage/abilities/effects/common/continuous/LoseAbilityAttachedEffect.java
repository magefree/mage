package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseAbilityAttachedEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final AttachmentType attachmentType;

    public LoseAbilityAttachedEffect(Ability ability, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
        this.attachmentType = attachmentType;
        this.staticText = attachmentType.verb() + " creature loses " + ability.getRule();
    }

    protected LoseAbilityAttachedEffect(final LoseAbilityAttachedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public LoseAbilityAttachedEffect copy() {
        return new LoseAbilityAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.removeAbility(ability, source.getSourceId(), game);
        return true;
    }

}
