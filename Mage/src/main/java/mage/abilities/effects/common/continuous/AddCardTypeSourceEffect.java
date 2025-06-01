package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author emerald000
 */
public class AddCardTypeSourceEffect extends ContinuousEffectImpl {

    private final List<CardType> addedCardTypes = new ArrayList<>();

    public AddCardTypeSourceEffect(Duration duration, CardType... addedCardType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        if (addedCardType.length == 0) {
            throw new IllegalArgumentException("AddCardTypeSourceEffect should be called with at least one card type.");
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

    protected AddCardTypeSourceEffect(final AddCardTypeSourceEffect effect) {
        super(effect);
        this.addedCardTypes.addAll(effect.addedCardTypes);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty() && this.duration == Duration.Custom) {
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
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()), game));
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        List<MageItem> objects = new ArrayList<>();
        for (MageObjectReference mor : affectedObjectList) {
            if (mor.refersTo(source.getSourceId(), game) || !duration.isOnlyValidIfNoZoneChange()) {
                objects.add(mor.getPermanent(game));
            }
        }
        return objects;
    }

    @Override
    public AddCardTypeSourceEffect copy() {
        return new AddCardTypeSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{this} becomes ");
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
        sb.append("in addition to its other types ").append(this.getDuration().toString());
        return sb.toString();
    }
}
