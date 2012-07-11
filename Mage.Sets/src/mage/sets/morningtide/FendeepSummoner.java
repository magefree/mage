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

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

/**
 * @author anonymous
 */
public class FendeepSummoner extends CardImpl<FendeepSummoner> {

    static final FilterLandPermanent filter = new FilterLandPermanent("Swamp");

    static {
        filter.add(new SubtypePredicate("Swamp"));
    }

    public FendeepSummoner(UUID ownerId) {
        super(ownerId, 61, "Fendeep Summoner", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Treefolk");
        this.subtype.add("Shaman");
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new FendeepSummonerToken(), "land", Constants.Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent(0, 2, filter, false));
        this.addAbility(ability);
    }

    public FendeepSummoner(final FendeepSummoner card) {
        super(card);
    }

    @Override
    public FendeepSummoner copy() {
        return new FendeepSummoner(this);
    }
}

class FendeepSummonerToken extends Token {

    public FendeepSummonerToken() {
        super("", "3/5 Treefolk Warrior");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Treefolk");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
    }
}
