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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author Plopman
 */
public class GenesisChamber extends CardImpl<GenesisChamber> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static{
        filter.add(Predicates.not(new TokenPredicate()));
    }
    public GenesisChamber(UUID ownerId) {
        super(ownerId, 122, "Genesis Chamber", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "DST";

        // Whenever a nontoken creature enters the battlefield, if Genesis Chamber is untapped, that creature's controller puts a 1/1 colorless Myr artifact creature token onto the battlefield.
        this.addAbility(new GenesisChamberTriggeredAbility(new GenesisChamberEffect(), filter));
    }

    public GenesisChamber(final GenesisChamber card) {
        super(card);
    }

    @Override
    public GenesisChamber copy() {
        return new GenesisChamber(this);
    }
}

class GenesisChamberTriggeredAbility extends EntersBattlefieldAllTriggeredAbility
{
    private static final String rule =  "Whenever a nontoken creature enters the battlefield, if {this} is untapped, that creature's controller puts a 1/1 colorless Myr artifact creature token onto the battlefield";
    public GenesisChamberTriggeredAbility(Effect effect, FilterPermanent filter)
    {
        super(Zone.BATTLEFIELD, effect, filter, false, true, rule);
    }
    
    public GenesisChamberTriggeredAbility(final GenesisChamberTriggeredAbility ability) {
        super(ability);
    }
    @Override
    public GenesisChamberTriggeredAbility copy() {
        return new GenesisChamberTriggeredAbility(this);
    }


    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = game.getPermanent(this.sourceId);
        if(permanent == null){
            permanent = (Permanent)game.getLastKnownInformation(sourceId, Zone.BATTLEFIELD);
        }
        if(permanent != null){
            return !permanent.isTapped();
        }
        return false;
    }
    
}


class GenesisChamberEffect extends OneShotEffect<GenesisChamberEffect> {

    public GenesisChamberEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller puts a 1/1 colorless Myr artifact creature token onto the battlefield";
    }

    public GenesisChamberEffect(final GenesisChamberEffect effect) {
        super(effect);
    }

    @Override
    public GenesisChamberEffect copy() {
        return new GenesisChamberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if(permanent == null){
            permanent = (Permanent)game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            MyrToken token = new MyrToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), permanent.getControllerId());
        }
        return true;
    }
}