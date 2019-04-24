
package mage.abilities.effects.common.continuous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * http://mtgsalvation.gamepedia.com/Land_changers
 *
 * @author LevelX2
 */
public class BecomesBasicLandTargetEffect extends ContinuousEffectImpl {

    protected boolean chooseLandType;
    protected List<SubType> landTypes = new ArrayList<>();
    protected List<SubType> landTypesToAdd = new ArrayList<>();
    protected boolean loseOther;  // loses all other abilities, card types, and creature types

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
        super(duration, Outcome.Detriment);
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
        this.staticText = setText();
        this.loseOther = loseOther;

    }

    public BecomesBasicLandTargetEffect(final BecomesBasicLandTargetEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
        this.landTypesToAdd.addAll(effect.landTypesToAdd);
        this.chooseLandType = effect.chooseLandType;
        this.loseOther = effect.loseOther;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
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
        if (loseOther) {
            landTypesToAdd.addAll(landTypes);
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID targetPermanent : targetPointer.getTargets(game, source)) {
            Permanent land = game.getPermanent(targetPermanent);
            if (land != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        // Attention: Cards like Unstable Frontier that use this class do not give the "Basic" supertype to the target
                        if (!land.isLand()) {
                            land.addCardType(CardType.LAND);
                        }
                        if (loseOther) {
                            // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                            // So the ability removing has to be done before Layer 6
                            land.removeAllAbilities(source.getSourceId(), game);
                            // 305.7
                            land.getSubtype(game).removeAll(SubType.getLandTypes(false));
                            land.getSubtype(game).addAll(landTypes);
                        } else {
                            landTypesToAdd.clear();
                            for (SubType subtype : landTypes) {
                                if (!land.hasSubtype(subtype, game)) {
                                    land.getSubtype(game).add(subtype);
                                    landTypesToAdd.add(subtype);
                                }
                            }
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        for (SubType landType : landTypesToAdd) {
                            switch (landType) {
                                case SWAMP:
                                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    break;
                                case MOUNTAIN:
                                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    break;
                                case FOREST:
                                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    break;
                                case ISLAND:
                                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    break;
                                case PLAINS:
                                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                    break;
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        if (chooseLandType) {
            sb.append("Target land becomes the basic land type of your choice");
        } else {
            sb.append("Target land becomes a ");
            int i = 1;
            for (SubType landType : landTypes) {
                if (i > 1) {
                    if (i == landTypes.size()) {
                        sb.append(" and ");
                    } else {
                        sb.append(", ");
                    }
                }
                i++;
                sb.append(landType);
            }
        }
        if (!duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
