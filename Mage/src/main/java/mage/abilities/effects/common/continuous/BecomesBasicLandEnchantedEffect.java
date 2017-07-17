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
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;

public class BecomesBasicLandEnchantedEffect extends ContinuousEffectImpl {

    protected final static ArrayList<String> allLandTypes = new ArrayList<>();


    protected ArrayList<String> landTypes = new ArrayList<>();

    public BecomesBasicLandEnchantedEffect(String... landNames) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        landTypes.addAll(Arrays.asList(landNames));
        this.staticText = setText();
    }

    public BecomesBasicLandEnchantedEffect(final BecomesBasicLandEnchantedEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesBasicLandEnchantedEffect copy() {
        return new BecomesBasicLandEnchantedEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        for (String landType : landTypes) {
                            switch (landType) {
                                case "Swamp":
                                    if (permanent.getSubtype(game).contains("Swamp")) { // type can be removed by other effect with newer timestamp, so no ability adding
                                        permanent.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case "Mountain":
                                    if (permanent.getSubtype(game).contains("Mountain")) {
                                        permanent.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case "Forest":
                                    if (permanent.getSubtype(game).contains("Forest")) {
                                        permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case "Island":
                                    if (permanent.getSubtype(game).contains("Island")) {
                                        permanent.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case "Plains":
                                    if (permanent.getSubtype(game).contains("Plains")) {
                                        permanent.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                            }
                        }
                        break;
                    case TypeChangingEffects_4:
                        // subtypes are all removed by changing the subtype to a land type.
                        permanent.getSubtype(game).removeAll(allLandTypes);
                        permanent.getSubtype(game).addAll(landTypes);
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Enchanted land is a ");
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
        return sb.toString();
    }
}
