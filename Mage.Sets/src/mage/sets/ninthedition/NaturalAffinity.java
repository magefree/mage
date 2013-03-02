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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class NaturalAffinity extends CardImpl<NaturalAffinity> {

    public NaturalAffinity(UUID ownerId) {
        super(ownerId, 256, "Natural Affinity", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "9ED";

        this.color.setGreen(true);

        // All lands become 2/2 creatures until end of turn. They're still lands.
        this.getSpellAbility().addEffect(new BecomesCreatureAllEffect());
    }

    public NaturalAffinity(final NaturalAffinity card) {
        super(card);
    }

    @Override
    public NaturalAffinity copy() {
        return new NaturalAffinity(this);
    }
}

class BecomesCreatureAllEffect extends ContinuousEffectImpl<BecomesCreatureAllEffect> {

  
    public BecomesCreatureAllEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.BecomeCreature);
        staticText = "All lands become 2/2 creatures until end of turn. They're still lands";
    }

    public BecomesCreatureAllEffect(final BecomesCreatureAllEffect effect) {
        super(effect);
    }

    @Override
    public BecomesCreatureAllEffect copy() {
        return new BecomesCreatureAllEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.affectedObjectsSet = true;
        for (Permanent perm: game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), source.getSourceId(), game)) {
            objects.add(perm.getId());
        }
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == Constants.SubLayer.NA) {
                    for(UUID uuid : objects){
                        Permanent permanent = game.getPermanent(uuid);
                        if(permanent != null){
                            permanent.getCardType().add(CardType.CREATURE);
                        }
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == Constants.SubLayer.SetPT_7b) {
                    for(UUID uuid : objects){
                        Permanent permanent = game.getPermanent(uuid);
                        if(permanent != null){
                            permanent.getPower().setValue(2);
                            permanent.getToughness().setValue(2);
                        }
                    }
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }


    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.PTChangingEffects_7 || layer == Constants.Layer.AbilityAddingRemovingEffects_6 || layer == Constants.Layer.ColorChangingEffects_5 || layer == Constants.Layer.TypeChangingEffects_4;
    }

}