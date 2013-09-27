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
package mage.sets.odyssey;

import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author cbt33, Loki (TakenoSamuraiGeneral), North (Coat of Arms)
 */
public class EarnestFellowship extends CardImpl<EarnestFellowship> {

    public EarnestFellowship(UUID ownerId) {
        super(ownerId, 21, "Earnest Fellowship", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EarnestFellowshipEffect()));

        // Each creature has protection from its colors.
        
    }

    public EarnestFellowship(final EarnestFellowship card) {
        super(card);
    }

    @Override
    public EarnestFellowship copy() {
        return new EarnestFellowship(this);
    }
}

class EarnestFellowshipEffect extends ContinuousEffectImpl<EarnestFellowshipEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
   

    public EarnestFellowshipEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Each creature has protection from its colors";
    }

    public EarnestFellowshipEffect(final EarnestFellowshipEffect effect) {
        super(effect);
    }

    @Override
    public EarnestFellowshipEffect copy() {
        return new EarnestFellowshipEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
            for (Permanent permanent : permanents) {
                objects.add(permanent.getId());
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (!this.affectedObjectsSet || objects.contains(permanent.getId())) {
                ObjectColor color = permanent.getColor();   
                FilterCard filterColor = new FilterCard("its own Colors");
               
                if (color.contains(ObjectColor.BLACK)){
                filterColor.add(new ColorPredicate(ObjectColor.BLACK));
                Ability ability = new ProtectionAbility(filterColor);
                permanent.addAbility(ability, id, game);
                }
                
                if (color.contains(ObjectColor.BLUE)){
                filterColor.add(new ColorPredicate(ObjectColor.BLUE));
                Ability ability = new ProtectionAbility(filterColor);
                permanent.addAbility(ability, id, game);
                }
                
                if (color.contains(ObjectColor.WHITE)){
                filterColor.add(new ColorPredicate(ObjectColor.WHITE));
                Ability ability = new ProtectionAbility(filterColor);
                permanent.addAbility(ability, id, game);
                }
                
                if (color.contains(ObjectColor.RED)){
                filterColor.add(new ColorPredicate(ObjectColor.RED));
                Ability ability = new ProtectionAbility(filterColor);
                permanent.addAbility(ability, id, game);
                }
                
                if (color.contains(ObjectColor.GREEN)){
                filterColor.add(new ColorPredicate(ObjectColor.GREEN));
                Ability ability = new ProtectionAbility(filterColor);
                permanent.addAbility(ability, id, game);
                }
            }
       }
        return true;
    }

}


