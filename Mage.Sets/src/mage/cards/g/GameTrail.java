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

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class GameTrail extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Mountain or Forest card from your hand");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.MOUNTAIN),
                new SubtypePredicate(SubType.FOREST)));
    }

    public GameTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Game Trail enters the battlefield, you may reveal a Mountain or Forest card from your hand. If you don't, Game Trail enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))),
                "you may reveal a Mountain or Forest card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {T}: Add {R} or {G} to your mana pool.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    public GameTrail(final GameTrail card) {
        super(card);
    }

    @Override
    public GameTrail copy() {
        return new GameTrail(this);
    }
}
