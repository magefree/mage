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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class StarkeOfRath extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }
    
    public StarkeOfRath(UUID ownerId) {
        super(ownerId, 205, "Starke of Rath", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "TMP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Destroy target artifact or creature. That permanent's controller gains control of Starke of Rath.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StarkeOfRathEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        
    }

    public StarkeOfRath(final StarkeOfRath card) {
        super(card);
    }

    @Override
    public StarkeOfRath copy() {
        return new StarkeOfRath(this);
    }
}

class StarkeOfRathEffect extends OneShotEffect {
    
    public StarkeOfRathEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or creature. That permanent's controller gains control of {this}";
    }
    
    public StarkeOfRathEffect(final StarkeOfRathEffect effect) {
        super(effect);
    }
    
    @Override
    public StarkeOfRathEffect copy() {
        return new StarkeOfRathEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());        
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                targetPermanent.destroy(source.getSourceId(), game, false);
            }
            Permanent sourcePermanent = (Permanent) source.getSourceObjectIfItStillExists(game);
            if (sourcePermanent != null && targetPermanent != null) {
                ContinuousEffect effect = new StarkeOfRathControlEffect();
                effect.setTargetPointer(new FixedTarget(targetPermanent.getControllerId()));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
}

class StarkeOfRathControlEffect extends ContinuousEffectImpl {

    public StarkeOfRathControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "That permanent's controller gains control of {this}";
    }

    public StarkeOfRathControlEffect(final StarkeOfRathControlEffect effect) {
        super(effect);
    }

    @Override
    public StarkeOfRathControlEffect copy() {
        return new StarkeOfRathControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        Player controller = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            return permanent.changeControllerId(getTargetPointer().getFirst(game, source), game);
        } else {
            discard();
        }
        return false;
    }

}
