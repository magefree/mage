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
package mage.cards.c;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author cg5
 */
public class CullingScales extends CardImpl {

    private static final FilterPermanent filterNonlandPermanentWithLowestCmc = new FilterNonlandPermanent(
        "nonland permanent with the lowest converted mana cost (<i>If two or more permanents are tied for lowest cost, target any one of them.</i>)"
    );
    static {
        filterNonlandPermanentWithLowestCmc.add(new HasLowestCMCAmongstNonlandPermanentsPredicate());
    }
    
    public CullingScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // At the beginning of your upkeep, destroy target nonland permanent with the lowest converted mana cost.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DestroyTargetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(filterNonlandPermanentWithLowestCmc));
        this.addAbility(ability);
    }

    public CullingScales(final CullingScales card) {
        super(card);
    }

    @Override
    public CullingScales copy() {
        return new CullingScales(this);
    }
    
}

class HasLowestCMCAmongstNonlandPermanentsPredicate implements Predicate<Permanent> {
    
    @Override
    public boolean apply(Permanent input, Game game) {
        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, input.getConvertedManaCost()));
        return !game.getBattlefield().contains(filter, 1, game);
    }
    
}