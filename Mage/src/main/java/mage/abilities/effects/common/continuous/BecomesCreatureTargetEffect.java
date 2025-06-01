package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BecomesCreatureTargetEffect extends ContinuousEffectImpl {

    protected Token token;
    protected boolean loseAllAbilities;
    protected boolean addStillALandText;
    protected boolean loseName;
    protected boolean keepAbilities;
    protected boolean removeSubtypes = false;
    protected boolean loseOtherCardTypes;

    protected boolean durationRuleAtStart = false; // put duration rule to the start of the rules instead end

    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration) {
        this(token, loseAllAbilities, stillALand, duration, false);
    }

    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration, boolean loseName) {
        this(token, loseAllAbilities, stillALand, duration, loseName, false);
    }

    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration, boolean loseName, boolean keepAbilities) {
        this(token, loseAllAbilities, stillALand, duration, loseName, keepAbilities, false);
    }

    /**
     * @param token
     * @param loseAllAbilities   loses all creature subtypes, colors and abilities
     * @param stillALand         add rule text, "it's still a land"
     * @param loseName           permanent loses name and gets it from token
     * @param keepAbilities      lose subtypes/colors, but keep abilities (example:
     *                           Scale Up)
     * @param duration
     * @param loseOtherCardTypes permanent loses other (original) card types, exclusively obtains card types of token
     */
    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration, boolean loseName,
                                       boolean keepAbilities, boolean loseOtherCardTypes) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.loseAllAbilities = loseAllAbilities;
        this.addStillALandText = stillALand;
        this.loseName = loseName;
        this.keepAbilities = keepAbilities;
        this.loseOtherCardTypes = loseOtherCardTypes;
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    protected BecomesCreatureTargetEffect(final BecomesCreatureTargetEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.loseAllAbilities = effect.loseAllAbilities;
        this.addStillALandText = effect.addStillALandText;
        this.loseName = effect.loseName;
        this.keepAbilities = effect.keepAbilities;
        this.loseOtherCardTypes = effect.loseOtherCardTypes;
        this.dependencyTypes.add(DependencyType.BecomeCreature);
        this.durationRuleAtStart = effect.durationRuleAtStart;
        this.removeSubtypes = effect.removeSubtypes;
    }

    @Override
    public BecomesCreatureTargetEffect copy() {
        return new BecomesCreatureTargetEffect(this);
    }

    public BecomesCreatureTargetEffect setRemoveSubtypes(boolean removeSubtypes) {
        this.removeSubtypes = removeSubtypes;
        return this;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (objects.isEmpty() && this.duration == Duration.Custom) {
            this.discard();
            return false;
        }
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TextChangingEffects_3:
                    if (loseName) {
                        permanent.setName(token.getName());
                    }
                    break;

                case TypeChangingEffects_4:
                    if (loseOtherCardTypes) {
                        permanent.removeAllCardTypes(game);
                    }
                    if (loseAllAbilities) {
                        permanent.removeAllCreatureTypes(game);
                    }
                    if (keepAbilities || removeSubtypes) {
                        permanent.removeAllSubTypes(game);
                    }
                    for (CardType t : token.getCardType(game)) {
                        permanent.addCardType(game, t);
                    }
                    permanent.copySubTypesFrom(game, token);

                    for (SuperType t : token.getSuperType(game)) {
                        permanent.addSuperType(game, t);
                    }

                    break;

                case ColorChangingEffects_5:
                    if (loseAllAbilities) {
                        permanent.getColor(game).setWhite(false);
                        permanent.getColor(game).setBlue(false);
                        permanent.getColor(game).setBlack(false);
                        permanent.getColor(game).setRed(false);
                        permanent.getColor(game).setGreen(false);
                    }
                    if (token.getColor(game).hasColor()) {
                        permanent.getColor(game).setColor(token.getColor(game));
                    }
                    break;

                case AbilityAddingRemovingEffects_6:
                    if (loseAllAbilities && !keepAbilities) {
                        permanent.removeAllAbilities(source.getSourceId(), game);
                    }
                    if (sublayer == SubLayer.NA) {
                        if (!token.getAbilities().isEmpty()) {
                            for (Ability ability : token.getAbilities()) {
                                permanent.addAbility(ability, source.getSourceId(), game, true);
                            }
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) { //  CDA can only define a characteristic of either the card or token it comes from.
                        permanent.getToughness().setModifiedBaseValue(token.getToughness().getValue());
                        permanent.getPower().setModifiedBaseValue(token.getPower().getValue());
                    }
            }
        }
        return true;
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
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public BecomesCreatureTargetEffect withDurationRuleAtStart(boolean durationRuleAtStart) {
        this.durationRuleAtStart = durationRuleAtStart;
        return this;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (durationRuleAtStart && !duration.toString().isEmpty()) {
            sb.append(duration.toString());
            sb.append(", ");
        }
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that creature"));
        sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " each" : "");
        if (loseAllAbilities && !keepAbilities) {
            sb.append(getTargetPointer().isPlural(mode.getTargets()) ?
                    " lose all their abilities and" :
                    " loses all abilities and");
        }
        sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " become " : " becomes a ");
        sb.append(token.getDescription());
        if (!durationRuleAtStart && !duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        if (addStillALandText) {
            sb.append(getTargetPointer().isPlural(mode.getTargets()) ? ". They're still lands" : ". It's still a land");
        }
        return sb.toString().replace(" .", ".");
    }

}
