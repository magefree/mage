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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CantBlockAllEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class GruulCharm extends CardImpl<GruulCharm> {
    
    final static private FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying");
    final static private FilterPermanent filter2 = new FilterPermanent("all permanents you own");
    final static private FilterCreaturePermanent filter3 = new FilterCreaturePermanent("creature with flying");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new OwnerPredicate(TargetController.YOU));
        filter3.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GruulCharm(UUID ownerId) {
        super(ownerId, 169, "Gruul Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{R}{G}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Choose one - Creatures without flying can't block this turn;
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Constants.Duration.EndOfTurn));
        
        // or gain control of all permanents you own;
        Mode mode = new Mode();
        mode.getEffects().add(new GainControlAllEffect(Constants.Duration.EndOfGame, filter2));
        this.getSpellAbility().addMode(mode);
        
        // or Gruul Charm deals 3 damage to each creature with flying.
        Mode mode2 = new Mode();
        mode2.getEffects().add(new DamageAllEffect(3, filter3));
        this.getSpellAbility().addMode(mode2);
    }

    public GruulCharm(final GruulCharm card) {
        super(card);
    }

    @Override
    public GruulCharm copy() {
        return new GruulCharm(this);
    }
}

class GainControlAllEffect extends ContinuousEffectImpl<GainControlAllEffect> {

    final FilterPermanent filter;

    public GainControlAllEffect(Constants.Duration duration, FilterPermanent filter) {
        super(duration, Constants.Layer.ControlChangingEffects_2, Constants.SubLayer.NA, Constants.Outcome.GainControl);
        this.filter = filter;
    }

    public GainControlAllEffect(final GainControlAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public GainControlAllEffect copy() {
        return new GainControlAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent != null) {
                permanent.changeControllerId(source.getControllerId(), game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of all permanents you own";
    }
}