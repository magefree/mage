package mage.abilities.effects.common.continuous;

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
        for (CardType cardType : addedCardType) {
            this.addedCardTypes.add(cardType);
            if (cardType == CardType.ENCHANTMENT) {
                dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
            } else if (cardType == CardType.ARTIFACT) {
                dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
            }
        }
    }

    public AddCardTypeSourceEffect(final AddCardTypeSourceEffect effect) {
        super(effect);
        this.addedCardTypes.addAll(effect.addedCardTypes);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && affectedObjectList.contains(new MageObjectReference(permanent, game))) {
            for (CardType cardType : addedCardTypes) {
                permanent.addCardType(game, cardType);
            }
            return true;
        } else if (this.getDuration() == Duration.Custom) {
            this.discard();
        }
        return false;
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
