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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.ExpansionSetPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public class GolgothianSylex extends CardImpl {

    public GolgothianSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {1}, {tap}: Each nontoken permanent from the Antiquities expansion is sacrificed by its controller.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GolgothianSylexEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());  
        this.addAbility(ability);
    }

    public GolgothianSylex(final GolgothianSylex card) {
        super(card);
    }

    @Override
    public GolgothianSylex copy() {
        return new GolgothianSylex(this);
    }
}

class GolgothianSylexEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.and(
                new ExpansionSetPredicate("ATQ"),
                Predicates.not(new TokenPredicate())
        ));
    }

    public GolgothianSylexEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each nontoken permanent from the Antiquities expansion is sacrificed by its controller.";
    }

    public GolgothianSylexEffect(final GolgothianSylexEffect effect) {
        super(effect);
    }

    @Override
    public GolgothianSylexEffect copy() {
        return new GolgothianSylexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
             permanent.sacrifice(source.getSourceId(), game);
        }
        return true;
    }
}