package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LevelX2
 */
public class BecomesCreatureAllEffect extends ContinuousEffectImpl {

    protected Token token;
    protected String theyAreStillType;
    private final FilterPermanent filter;
    private boolean loseColor = true;
    private boolean loseTypes = false;
    protected boolean loseName = false;

    public BecomesCreatureAllEffect(Token token, String theyAreStillType,
                                    FilterPermanent filter, Duration duration, boolean loseColor) {
        this(token, theyAreStillType, filter, duration, loseColor, false, false);
    }

    public BecomesCreatureAllEffect(Token token, String theyAreStillType,
                                    FilterPermanent filter, Duration duration, boolean loseColor, boolean loseName) {
        this(token, theyAreStillType, filter, duration, loseColor, loseName, false);
    }

    public BecomesCreatureAllEffect(Token token, String theyAreStillType,
                                    FilterPermanent filter, Duration duration, boolean loseColor, boolean loseName, boolean loseTypes) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.theyAreStillType = theyAreStillType;
        this.filter = filter;
        this.loseColor = loseColor;
        this.loseName = loseName;
        this.loseTypes = loseTypes;

        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    public BecomesCreatureAllEffect(final BecomesCreatureAllEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.theyAreStillType = effect.theyAreStillType;
        this.filter = effect.filter.copy();
        this.loseColor = effect.loseColor;
        this.loseName = effect.loseName;
        this.loseTypes = effect.loseTypes;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(
                    filter, source.getControllerId(), source, game)) {
                affectedObjectList.add(new MageObjectReference(perm, game));
            }
        }
    }

    @Override
    public BecomesCreatureAllEffect copy() {
        return new BecomesCreatureAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Set<Permanent> affectedPermanents = new HashSet<>();
        if (this.affectedObjectsSet) {
            for (MageObjectReference ref : affectedObjectList) {
                affectedPermanents.add(ref.getPermanent(game));
            }
        } else {
            affectedPermanents = new HashSet<>(game.getBattlefield()
                    .getActivePermanents(filter, source.getControllerId(), source, game));
        }

        for (Permanent permanent : affectedPermanents) {
            if (permanent == null) {
                continue;
            }
            switch (layer) {
                case TextChangingEffects_3:
                    if (loseName) {
                        permanent.setName(token.getName());
                    }
                    break;

                case TypeChangingEffects_4:
                    for (CardType t : token.getCardType(game)) {
                        permanent.addCardType(game, t);
                    }
                    if (theyAreStillType != null || loseTypes) {
                        permanent.removeAllCreatureTypes(game);
                    }
                    permanent.copySubTypesFrom(game, token);

                    for (SuperType t : token.getSuperType()) {
                        if (!permanent.getSuperType().contains(t)) {
                            permanent.addSuperType(t);
                        }
                    }

                    break;

                case ColorChangingEffects_5:
                    if (this.loseColor) {
                        permanent.getColor(game).setWhite(false);
                        permanent.getColor(game).setBlue(false);
                        permanent.getColor(game).setBlack(false);
                        permanent.getColor(game).setRed(false);
                        permanent.getColor(game).setGreen(false);
                    }
                    if (token.getColor(game).hasColor()) {
                        permanent.getColor(game).addColor(token.getColor(game));
                    }
                    break;

                case AbilityAddingRemovingEffects_6:
                    if (!token.getAbilities().isEmpty()) {
                        for (Ability ability : token.getAbilities()) {
                            permanent.addAbility(ability, source.getSourceId(), game);
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int power = token.getPower().getValue();
                        int toughness = token.getToughness().getValue();
                        if (power != 0 && toughness != 0) {
                            permanent.getPower().setValue(power);
                            permanent.getToughness().setValue(toughness);
                        }
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4
                || layer == Layer.TextChangingEffects_3;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (duration.toString() != null && !duration.toString().isEmpty()) {
            sb.append(duration.toString()).append(", ");
        }
        sb.append(filter.getMessage());
        if (duration.toString() != null && duration.toString().isEmpty()) {
            sb.append(" are ");
        } else {
            sb.append(" become ");
        }
        sb.append(token.getDescription());
        if (theyAreStillType != null && !theyAreStillType.isEmpty()) {
            sb.append(". They're still ").append(theyAreStillType);
        }
        return sb.toString();
    }

}
