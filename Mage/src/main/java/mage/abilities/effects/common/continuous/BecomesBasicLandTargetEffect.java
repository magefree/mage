/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * http://mtgsalvation.gamepedia.com/Land_changers
 *
 * @author LevelX2
 */
public class BecomesBasicLandTargetEffect extends ContinuousEffectImpl {

    protected boolean chooseLandType;
    protected ArrayList<String> landTypes = new ArrayList();
    protected ArrayList<String> landTypesToAdd = new ArrayList();
    protected boolean loseOther;  // loses all other abilities, card types, and creature types

    public BecomesBasicLandTargetEffect(Duration duration) {
        this(duration, true, new String[0]);
    }

    public BecomesBasicLandTargetEffect(Duration duration, String... landNames) {
        this(duration, false, landNames);
    }

    public BecomesBasicLandTargetEffect(Duration duration, boolean chooseLandType, String... landNames) {
        this(duration, chooseLandType, true, landNames);
    }

    public BecomesBasicLandTargetEffect(Duration duration, boolean chooseLandType, boolean loseOther, String... landNames) {
        super(duration, Outcome.Detriment);
        this.landTypes.addAll(Arrays.asList(landNames));
        if (landTypes.contains("Mountain")) {
            dependencyTypes.add(DependencyType.BecomeMountain);
        }
        if (landTypes.contains("Forest")) {
            dependencyTypes.add(DependencyType.BecomeForest);
        }
        if (landTypes.contains("Swamp")) {
            dependencyTypes.add(DependencyType.BecomeSwamp);
        }
        if (landTypes.contains("Island")) {
            dependencyTypes.add(DependencyType.BecomeIsland);
        }
        if (landTypes.contains("Plains")) {
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
            if (controller != null) {
                Choice choice = new ChoiceBasicLandType();
                controller.choose(outcome, choice, game);
                landTypes.add(choice.getChoice());
            } else {
                this.discard();
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
                            for (String subtype : landTypes) {
                                if (!land.getSubtype(game).contains(subtype)) {
                                    land.getSubtype(game).add(subtype);
                                    landTypesToAdd.add(subtype);
                                }
                            }
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        for (String landType : landTypesToAdd) {
                            switch (landType) {
                                case "Swamp":
                                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    break;
                                case "Mountain":
                                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    break;
                                case "Forest":
                                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    break;
                                case "Island":
                                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    break;
                                case "Plains":
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
            sb.append("Target land becomes the basic land type of your choice ");
        } else {
            sb.append("Target land becomes a ");
            int i = 1;
            for (String landType : landTypes) {
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
