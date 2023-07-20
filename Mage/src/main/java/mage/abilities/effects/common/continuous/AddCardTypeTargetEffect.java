package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author nantuko
 */
public class AddCardTypeTargetEffect extends ContinuousEffectImpl {

    private final List<CardType> addedCardTypes = new ArrayList<>();

    public AddCardTypeTargetEffect(Duration duration, CardType... addedCardType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        for (CardType cardType : addedCardType) {
            this.addedCardTypes.add(cardType);
            if (cardType == CardType.ENCHANTMENT) {
                dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
            } else if (cardType == CardType.ARTIFACT) {
                dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
            }
        }

    }

    public AddCardTypeTargetEffect(final AddCardTypeTargetEffect effect) {
        super(effect);
        this.addedCardTypes.addAll(effect.addedCardTypes);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(targetId);
            if (target != null) {
                for (CardType cardType : addedCardTypes) {
                    target.addCardType(game, cardType);
                }
                result = true;
            }
        }
        if (!result) {
            if (this.getDuration() == Duration.Custom) {
                this.discard();
            }
        }
        return result;
    }

    @Override
    public AddCardTypeTargetEffect copy() {
        return new AddCardTypeTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " become " : " becomes ");
        boolean article = false;
        for (CardType cardType : addedCardTypes) {
            if (!article) {
                if (cardType.toString().startsWith("A") || cardType.toString().startsWith("E")) {
                    sb.append("an ");
                } else {
                    sb.append("a ");
                }
                article = true;
            }
            sb.append(cardType.toString().toLowerCase(Locale.ENGLISH)).append(" ");
        }
        sb.append("in addition to its other types");
        if (getDuration() == Duration.EndOfTurn) {
            sb.append(" until end of turn");
        }
        return sb.toString();
    }
}
