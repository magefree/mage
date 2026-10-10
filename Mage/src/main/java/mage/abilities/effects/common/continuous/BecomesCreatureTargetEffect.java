package mage.abilities.effects.common.continuous;

import java.util.*;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

/**
 * Target permanent becomes the given token's characteristics
 * Related rules: CR 205.1a, 205.1b, 205.4b, 105.3
 * <p>
 * Becomes:
 * - card types: replace all others due to 205.1a, except "artifact creature" (205.1b);
 *   creature type of the token is ignored for it (limited by CreatureToken implementation - it's always there),
 *   so a token with creature type only adds it and keeps others (e.g. "becomes a blue Frog");
 *   use withLoseOtherCardTypes for "becomes a ... creature"
 *   TODO: refactor to implement non-creature fake token, so no needs to workaround with ignore creature type
 * - subtypes: replace only the same subtype set and keep others (e.g. replace creature's but keep artifact's),
 *   subtypes of a removed card type go with it (205.1a)
 * - supertypes: add (205.4b)
 * - color: replace all, if token has color (105.3)
 * - abilities: add
 * - PT: set base
 * <p>
 * Settings to keep or lose existing data:
 * - stillALand: "It's still a land" - keep all prior types (205.1b)
 * - withKeepPriorTypes: "in addition to its other types" - keep all prior types (205.1b)
 * - withKeepPriorColors: "in addition to its other colors" - add color instead replace (105.3)
 * - withLoseOtherCardTypes: "becomes a ... creature" - replace card types incl. creature's (205.1a), TODO: need refactor, see above
 * - loseAllAbilities: "loses all abilities"
 * - loseName: "named ..."
 *
 * @author JayDi85
 */
public class BecomesCreatureTargetEffect extends ContinuousEffectImpl {

    protected Token token;
    protected boolean loseAllAbilities;
    protected boolean addStillALandText;
    protected boolean loseName;
    protected boolean loseOtherCardTypes = false;
    protected boolean keepPriorTypes;
    protected boolean keepPriorColors = false;

    protected boolean durationRuleAtStart = false; // put duration rule to the start of the rules instead end

    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration) {
        this(token, loseAllAbilities, stillALand, duration, false);
    }

    /**
     * @param token            characteristics the permanent becomes
     * @param loseAllAbilities permanent loses all other abilities (keeps only the token's abilities)
     * @param stillALand       "it's still a land" - add the rule text and keep all prior types and subtypes
     * @param duration
     * @param loseName         permanent gets the token's name TODO: refactor loseName as builder style, not param
     */
    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration, boolean loseName) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.loseAllAbilities = loseAllAbilities;
        this.addStillALandText = stillALand;
        this.loseName = loseName;
        this.keepPriorTypes = stillALand;
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    protected BecomesCreatureTargetEffect(final BecomesCreatureTargetEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.loseAllAbilities = effect.loseAllAbilities;
        this.addStillALandText = effect.addStillALandText;
        this.loseName = effect.loseName;
        this.loseOtherCardTypes = effect.loseOtherCardTypes;
        this.keepPriorTypes = effect.keepPriorTypes;
        this.keepPriorColors = effect.keepPriorColors;
        this.dependencyTypes.add(DependencyType.BecomeCreature);
        this.durationRuleAtStart = effect.durationRuleAtStart;
    }

    @Override
    public BecomesCreatureTargetEffect copy() {
        return new BecomesCreatureTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean result = false;
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
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
                    applyTypes(permanent, game);
                    break;

                case ColorChangingEffects_5:
                    // 105.3: a new color replaces all previous colors (unless "in addition"), no color - nothing changes
                    if (token.getColor(game).hasColor()) {
                        if (keepPriorColors) {
                            permanent.getColor(game).addColor(token.getColor(game));
                        } else {
                            permanent.getColor(game).setColor(token.getColor(game));
                        }
                    }
                    break;

                case AbilityAddingRemovingEffects_6:
                    if (loseAllAbilities) {
                        permanent.removeAllAbilities(source.getSourceId(), game);
                    }
                    if (sublayer == SubLayer.NA) {
                        for (Ability ability : token.getAbilities()) {
                            permanent.addAbility(ability, source.getSourceId(), game, true);
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) { //  CDA can only define a characteristic of either the card or token it comes from.
                        permanent.getToughness().setModifiedBaseValue(token.getToughness().getValue());
                        permanent.getPower().setModifiedBaseValue(token.getPower().getValue());
                    }
            }
            result = true;
        }
        if (!result && this.duration == Duration.Custom) {
            this.discard();
        }
        return result;
    }

    private void applyTypes(Permanent permanent, Game game) {
        // card types: token's replace all others (205.1a) or keep with "in addition", "still a land" (205.1b)
        List<CardType> newCardTypes = new ArrayList<>();
        if (keepPriorTypes || (!loseOtherCardTypes && !isTokenSetCardTypes(game))) {
            newCardTypes.addAll(permanent.getCardType(game));
        }
        newCardTypes.addAll(token.getCardType(game));

        // subtypes: token's subtype sets replace the same sets (205.1a) or keep all (205.1b)
        Set<SubTypeSet> replacedSubTypeSets = EnumSet.noneOf(SubTypeSet.class);
        if (!keepPriorTypes) {
            token.getSubtype(game).forEach(subType -> replacedSubTypeSets.add(subType.getSubTypeSet()));
            if (token.isAllCreatureTypes(game)) {
                replacedSubTypeSets.add(SubTypeSet.CreatureType);
            }
            if (replacedSubTypeSets.contains(SubTypeSet.BasicLandType) || replacedSubTypeSets.contains(SubTypeSet.NonBasicLandType)) {
                // basic and nonbasic land types are one set of land types
                replacedSubTypeSets.add(SubTypeSet.BasicLandType);
                replacedSubTypeSets.add(SubTypeSet.NonBasicLandType);
            }
        }
        List<SubType> newSubTypes = new ArrayList<>();
        permanent.getSubtype(game).stream()
                .filter(subType -> !replacedSubTypeSets.contains(subType.getSubTypeSet()))
                .forEach(newSubTypes::add);
        newSubTypes.addAll(token.getSubtype(game));
        boolean newAllCreatureTypes = token.isAllCreatureTypes(game)
                || (permanent.isAllCreatureTypes(game) && !replacedSubTypeSets.contains(SubTypeSet.CreatureType));
        boolean newAllNonbasicLandTypes = permanent.isAllNonbasicLandTypes(game)
                && !replacedSubTypeSets.contains(SubTypeSet.NonBasicLandType);

        // apply card types first - subtypes of a missing card type can't be gained (205.1a, 205.3d, see SubType.canGain)
        permanent.removeAllCardTypes(game);
        permanent.addCardType(game, newCardTypes.toArray(new CardType[0]));
        permanent.removeAllSubTypes(game);
        permanent.addSubType(game, newSubTypes);
        permanent.setIsAllCreatureTypes(game, newAllCreatureTypes && (permanent.isCreature(game) || permanent.isKindred(game)));
        permanent.setIsAllNonbasicLandTypes(game, newAllNonbasicLandTypes && permanent.isLand(game));

        // supertypes are added, prior supertypes stay (205.4b)
        token.getSuperType(game).forEach(superType -> permanent.addSuperType(game, superType));
    }

    /**
     * Token sets the card types of the permanent (205.1a), e.g. "becomes a Treasure artifact"
     */
    private boolean isTokenSetCardTypes(Game game) {
        List<CardType> tokenCardTypes = token.getCardType(game);
        if (tokenCardTypes.contains(CardType.ARTIFACT) && tokenCardTypes.contains(CardType.CREATURE)) {
            // "artifact creature" keeps all prior card types (205.1b)
            return false;
        }
        // ignore creature type - it is always there (limited by CreatureToken implementation)
        return tokenCardTypes.stream().anyMatch(t -> t != CardType.CREATURE);
    }

    /**
     * "becomes a ... creature", "loses all other card types": all card types of the permanent are replaced
     * by the token's card types (205.1a), incl. its creature type
     */
    public BecomesCreatureTargetEffect withLoseOtherCardTypes(boolean loseOtherCardTypes) {
        this.loseOtherCardTypes = loseOtherCardTypes;
        return this;
    }

    /**
     * "in addition to its other types": all prior card types and subtypes are kept (205.1b)
     */
    public BecomesCreatureTargetEffect withKeepPriorTypes(boolean keepPriorTypes) {
        this.keepPriorTypes = keepPriorTypes;
        return this;
    }

    /**
     * "in addition to its other colors": the token's color is added to prior colors (105.3)
     */
    public BecomesCreatureTargetEffect withKeepPriorColors(boolean keepPriorColors) {
        this.keepPriorColors = keepPriorColors;
        return this;
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
        if (loseAllAbilities) {
            sb.append(getTargetPointer().isPlural(mode.getTargets()) ?
                    " lose all their abilities and" :
                    " loses all abilities and");
        }
        if (getTargetPointer().isPlural(mode.getTargets())) {
            sb.append(" become ").append(token.getDescription());
        } else {
            sb.append(" becomes ").append(CardUtil.addArticle(token.getDescription()));
        }
        if (!durationRuleAtStart && !duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        if (addStillALandText) {
            sb.append(getTargetPointer().isPlural(mode.getTargets()) ? ". They're still lands" : ". It's still a land");
        }
        return sb.toString().replace(" .", ".");
    }

}
