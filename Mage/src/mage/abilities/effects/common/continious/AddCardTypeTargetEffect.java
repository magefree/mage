package mage.abilities.effects.common.continious;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AddCardTypeTargetEffect extends ContinuousEffectImpl<AddCardTypeTargetEffect> {
    private Constants.CardType addedCardType;

    public AddCardTypeTargetEffect(Constants.CardType addedCardType, Constants.Duration duration) {
        super(duration, Constants.Layer.TypeChangingEffects_4, Constants.SubLayer.NA, Constants.Outcome.Benefit);
        this.addedCardType = addedCardType;
    }

    public AddCardTypeTargetEffect(final AddCardTypeTargetEffect effect) {
        super(effect);
        this.addedCardType = effect.addedCardType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(source));
        if (target != null) {
            if (!target.getCardType().contains(addedCardType))
                target.getCardType().add(addedCardType);
        }
        return false;
    }

    @Override
    public AddCardTypeTargetEffect copy() {
        return new AddCardTypeTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Target ").append(mode.getTargets().get(0).getTargetName()).append(" becomes ").append(addedCardType.toString()).append(" in addition to its other types until end of turn");
        return sb.toString();
    }
}
