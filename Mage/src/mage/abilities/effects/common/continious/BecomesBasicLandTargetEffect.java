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

package mage.abilities.effects.common.continious;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.choices.Choice;
import mage.choices.ChoiceBasicLandType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */


public class BecomesBasicLandTargetEffect extends ContinuousEffectImpl<BecomesBasicLandTargetEffect> {

    protected boolean chooseLandType;
    protected ArrayList<String> landTypes = new ArrayList();

    public BecomesBasicLandTargetEffect(Duration duration) {
        this(duration, true, new String[0]);
    }

    public BecomesBasicLandTargetEffect(Duration duration, String... landNames) {
        this(duration, false, landNames);
    }

    public BecomesBasicLandTargetEffect(Duration duration, boolean chooseLandType, String... landNames) {
        super(duration, Outcome.Detriment);
        landTypes.addAll(Arrays.asList(landNames));
        this.chooseLandType = chooseLandType;
        this.staticText = setText();

    }

    public BecomesBasicLandTargetEffect(final BecomesBasicLandTargetEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
        this.chooseLandType = effect.chooseLandType;
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
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID targetPermanent : targetPointer.getTargets(game, source)) {
            Permanent land = game.getPermanent(targetPermanent);
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        land.removeAllAbilities(source.getSourceId(), game);
                        for (String landType : landTypes)  {
                            if (landType.equals("Swamp")) {
                                land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                            } else if (landType.equals("Mountain")) {
                                land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                            } else if (landType.equals("Forest")) {
                                land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                            } else if (landType.equals("Island")) {
                                land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                            } else if (landType.equals("Plains")) {
                                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                            }
                        }
                        break;
                    case TypeChangingEffects_4:
                        if (!land.getCardType().contains(CardType.LAND)) {
                            land.getCardType().add(CardType.LAND);
                        }
                        land.getSubtype().clear();
                        land.getSubtype().addAll(landTypes);
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
            for (String landType : landTypes)  {
                if (i >1) {
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
        if (!duration.toString().isEmpty() && !duration.equals(Duration.EndOfGame)) {
                sb.append(" ").append(duration.toString());
        }
        return sb.toString();
    }
}
