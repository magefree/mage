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
package mage.sets.morningtide;

import java.util.ArrayList;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksCreatureYourControlTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class SharedAnimosity extends CardImpl<SharedAnimosity> {

    public SharedAnimosity(UUID ownerId) {
        super(ownerId, 104, "Shared Animosity", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "MOR";

        this.color.setRed(true);

        // Whenever a creature you control attacks, it gets +1/+0 until end of turn for each other attacking creature that shares a creature type with it.
        this.addAbility(new AttacksCreatureYourControlTriggeredAbility(new SharedAnimosityEffect(), false, true));
    }

    public SharedAnimosity(final SharedAnimosity card) {
        super(card);
    }

    @Override
    public SharedAnimosity copy() {
        return new SharedAnimosity(this);
    }
}

class SharedAnimosityEffect extends ContinuousEffectImpl<SharedAnimosityEffect> {

    private int power;

    public SharedAnimosityEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        
    }

    public SharedAnimosityEffect(final SharedAnimosityEffect effect) {
        super(effect);
        this.power = effect.power;
    }

    @Override
    public SharedAnimosityEffect copy() {
        return new SharedAnimosityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent permanent = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (permanent != null) {
            
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.not(new PermanentIdPredicate(this.targetPointer.getFirst(game, source))));
            filter.add(new AttackingPredicate());
            boolean isChangeling = false;
            for(Ability ability : permanent.getAbilities()){
                if(ability instanceof ChangelingAbility){
                    isChangeling = true;
                }
            }   
            if(!isChangeling){
                ArrayList<Predicate<MageObject>> predicateList = new ArrayList<Predicate<MageObject>>();
                for(String subtype : permanent.getSubtype()){
                    predicateList.add(new SubtypePredicate(subtype));
                }
                filter.add(Predicates.or(predicateList));
            }
            
            power = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getId(), game).size();
        }
        targetPointer.init(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = (Permanent) game.getPermanent(this.targetPointer.getFirst(game, source));
        if (target != null) {
            target.addPower(power);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "it gets +1/+0 until end of turn for each other attacking creature that shares a creature type with it";
    }
}
