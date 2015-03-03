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
package mage.sets.legends;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Sentinel extends CardImpl {

    public Sentinel(UUID ownerId) {
        super(ownerId, 239, "Sentinel", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "LEG";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking or blocked by Sentinel");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                                 new BlockingAttackerIdPredicate(this.getId())));        
        // 0: Change Sentinel's base toughness to 1 plus the power of target creature blocking or blocked by Sentinel. (This effect lasts indefinitely.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SentinelEffect(), new GenericManaCost(0));
        ability.addTarget(null);
        ability.addTarget(new TargetCreaturePermanent(filter));        
        this.addAbility(ability);
        
    }

    public Sentinel(final Sentinel card) {
        super(card);
    }

    @Override
    public Sentinel copy() {
        return new Sentinel(this);
    }
}

class SentinelEffect extends OneShotEffect {
    
    public SentinelEffect() {
        super(Outcome.Detriment);
        this.staticText = "Change {this}'s base toughness to 1 plus the power of target creature blocking or blocked by {this}. <i>(This effect lasts indefinitely.)</i>";
    }
    
    public SentinelEffect(final SentinelEffect effect) {
        super(effect);
    }
    
    @Override
    public SentinelEffect copy() {
        return new SentinelEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (controller != null && targetPermanent != null) {
            int newToughness = targetPermanent.getPower().getValue() + 1;
            game.addEffect(new SetToughnessSourceEffect(new StaticValue(newToughness), Duration.Custom), source);
            return true;
        }
        return false;
    }
}
