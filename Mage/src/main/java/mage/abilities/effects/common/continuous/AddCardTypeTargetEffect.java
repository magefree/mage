package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nantuko
 */
public class AddCardTypeTargetEffect extends ContinuousEffectImpl {

    private final List<CardType> addedCardTypes = new ArrayList<>();

    public AddCardTypeTargetEffect(Duration duration, CardType... addedCardType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        if (addedCardType.length == 0) {
            throw new IllegalArgumentException("AddCardTypeTargetEffect should be called with at least one card type.");
        }
        for (CardType cardType : addedCardType) {
            this.addedCardTypes.add(cardType);
            if (cardType == CardType.ENCHANTMENT) {
                dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
            } else if (cardType == CardType.ARTIFACT) {
                dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
            } else if (cardType == CardType.LAND) {
                dependencyTypes.add(DependencyType.BecomeNonbasicLand);
            }
        }

    }

    protected AddCardTypeTargetEffect(final AddCardTypeTargetEffect effect) {
        super(effect);
        this.addedCardTypes.addAll(effect.addedCardTypes);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty() && this.getDuration() == Duration.Custom) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addCardType(game, addedCardTypes.toArray(new CardType[0]));
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
