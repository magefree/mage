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

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class NaturalAffinity extends CardImpl {

    public NaturalAffinity(UUID ownerId) {
        super(ownerId, 256, "Natural Affinity", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "9ED";


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

class BecomesCreatureAllEffect extends ContinuousEffectImpl {

  
    public BecomesCreatureAllEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
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
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { 
                        Permanent permanent = it.next().getPermanent(game);
                        if(permanent != null){
                            permanent.getCardType().add(CardType.CREATURE);
                        } else {
                            it.remove();
                        }
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                        Permanent permanent = it.next().getPermanent(game);
                        if(permanent != null){
                            permanent.getPower().setValue(2);
                            permanent.getToughness().setValue(2);
                        } else {
                            it.remove();
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
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
