package mage.abilities.effects.common.continuous;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * http://mtgsalvation.gamepedia.com/Land_changers
 *
 * @author LevelX2
 */
public class BecomesBasicLandTargetEffect extends ContinuousEffectImpl {

    protected boolean chooseLandType;
    protected List<SubType> landTypes = new ArrayList<>();
    private final boolean loseOther;  // loses all other abilities, card types, and creature types

    public BecomesBasicLandTargetEffect(Duration duration) {
        this(duration, true);
    }

    public BecomesBasicLandTargetEffect(Duration duration, SubType... landNames) {
        this(duration, false, landNames);
    }

    public BecomesBasicLandTargetEffect(Duration duration, boolean chooseLandType, SubType... landNames) {
        this(duration, chooseLandType, true, landNames);
    }

    public BecomesBasicLandTargetEffect(Duration duration, boolean chooseLandType, boolean loseOther, SubType... landNames) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.landTypes.addAll(Arrays.asList(landNames));
        if (landTypes.contains(SubType.MOUNTAIN)) {
            dependencyTypes.add(DependencyType.BecomeMountain);
        }
        if (landTypes.contains(SubType.FOREST)) {
            dependencyTypes.add(DependencyType.BecomeForest);
        }
        if (landTypes.contains(SubType.SWAMP)) {
            dependencyTypes.add(DependencyType.BecomeSwamp);
        }
        if (landTypes.contains(SubType.ISLAND)) {
            dependencyTypes.add(DependencyType.BecomeIsland);
        }
        if (landTypes.contains(SubType.PLAINS)) {
            dependencyTypes.add(DependencyType.BecomePlains);
        }
        this.chooseLandType = chooseLandType;
        this.loseOther = loseOther;
    }

    public BecomesBasicLandTargetEffect(final BecomesBasicLandTargetEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
        this.chooseLandType = effect.chooseLandType;
        this.loseOther = effect.loseOther;
    }

    @Override
    public BecomesBasicLandTargetEffect copy() {
        return new BecomesBasicLandTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // choose land type
        if (chooseLandType) {
            Player controller = game.getPlayer(source.getControllerId());
            Choice choice = new ChoiceBasicLandType();
            if (controller != null && controller.choose(outcome, choice, game)) {
                landTypes.add(SubType.byDescription(choice.getChoice()));
            } else {
                this.discard();
                return;
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetPermanent : targetPointer.getTargets(game, source)) {
            Permanent land = game.getPermanent(targetPermanent);
            if (land == null) {
                continue;
            }
            if (!land.isLand(game)) {
                land.addCardType(game, CardType.LAND);
            }
            if (loseOther) {
                // 305.7 Note that this doesn't remove any abilities
                // that were granted to the land by other effects
                // So the ability removing has to be done before Layer 6
                land.removeAllAbilities(source.getSourceId(), game);
                // 305.7
                land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
            }
            land.addSubType(game, landTypes);

            // add intrinsic land abilities here not in layer 6
            Abilities<Ability> landAbilities = land.getAbilities(game);
            for (SubType landType : landTypes) {
                switch (landType) {
                    case PLAINS:
                        if (!landAbilities.containsClass(WhiteManaAbility.class)) {
                            land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case ISLAND:
                        if (!landAbilities.containsClass(BlueManaAbility.class)) {
                            land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case SWAMP:
                        if (!landAbilities.containsClass(BlackManaAbility.class)) {
                            land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case MOUNTAIN:
                        if (!landAbilities.containsClass(RedManaAbility.class)) {
                            land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case FOREST:
                        if (!landAbilities.containsClass(GreenManaAbility.class)) {
                            land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("target ");
        if (!mode.getTargets().isEmpty()) {
            sb.append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("land");
        }
        sb.append(" becomes ");
        if (chooseLandType) {
            sb.append("the basic land type of your choice");
        } else {
            sb.append(CardUtil.addArticle(CardUtil.concatWithAnd(landTypes
                    .stream()
                    .map(SubType::getDescription)
                    .collect(Collectors.toList())
            )));
        }
        if (!loseOther) {
            sb.append(" in addition to its other types");
        }
        if (!duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration);
        }
        return sb.toString();
    }
}
