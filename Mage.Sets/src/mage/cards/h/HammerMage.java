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
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public class HammerMage extends CardImpl {

   public HammerMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add("Human");
        this.subtype.add("Spellshaper");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{R}, {tap}, Discard a card: Destroy all artifacts with converted mana cost X or less.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HammerMageEffect(), new ManaCostsImpl("{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public HammerMage(final HammerMage card) {
        super(card);
    }

    @Override
    public HammerMage copy() {
        return new HammerMage(this);
    }
}

class HammerMageEffect extends  OneShotEffect {

    public HammerMageEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all artifacts with converted mana cost X or less";
    }

    public HammerMageEffect(final HammerMageEffect effect) {
        super(effect);
    }

    @Override
    public HammerMageEffect copy() {
        return new HammerMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterArtifactPermanent filter = new FilterArtifactPermanent();
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
        for(Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }
}
