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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.PTChangingEffects_7;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class LifeAndLimb extends CardImpl {

    public LifeAndLimb(UUID ownerId) {
        super(ownerId, 133, "Life and Limb", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "PLC";

        // All Forests and all Saprolings are 1/1 green Saproling creatures and Forest lands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LifeAndLimbEffect()));
    }

    public LifeAndLimb(final LifeAndLimb card) {
        super(card);
    }

    @Override
    public LifeAndLimb copy() {
        return new LifeAndLimb(this);
    }
}

class LifeAndLimbEffect extends ContinuousEffectImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("All Forests and all Saprolings");
    static {
        filter.add(Predicates.or(new SubtypePredicate("Forest"), new SubtypePredicate("Saproling")));
    }

    LifeAndLimbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All Forests and all Saprolings are 1/1 green Saproling creatures and Forest lands in addition to their other types";
    }

    LifeAndLimbEffect(final LifeAndLimbEffect effect) {
        super(effect);
    }

    @Override
    public LifeAndLimbEffect copy() {
        return new LifeAndLimbEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (!permanent.getCardType().contains(CardType.CREATURE)) {
                            permanent.getCardType().add(CardType.CREATURE);
                        }
                        if (!permanent.getSubtype().contains("Saproling")) {
                            permanent.getSubtype().add("Saproling");
                        }
                        if (!permanent.getCardType().contains(CardType.LAND)) {
                            permanent.getCardType().add(CardType.LAND);
                        }
                        if (!permanent.getSubtype().contains("Forest")) {
                            permanent.getSubtype().add("Forest");
                        }
                        break;
                    case ColorChangingEffects_5:
                        permanent.getColor().setColor(ObjectColor.GREEN);
                        break;
                    case AbilityAddingRemovingEffects_6:
                        boolean flag = false;
                        for (Ability ability : permanent.getAbilities(game)) {
                            if (ability instanceof GreenManaAbility) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer.equals(SubLayer.SetPT_7b)) {
                            permanent.getPower().setValue(1);
                            permanent.getToughness().setValue(1);
                        }
                        break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.AbilityAddingRemovingEffects_6 
                || layer == Layer.PTChangingEffects_7;
    }
}
