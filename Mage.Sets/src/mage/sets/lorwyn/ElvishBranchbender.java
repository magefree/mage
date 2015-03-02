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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public class ElvishBranchbender extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Forest");
    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    public ElvishBranchbender(UUID ownerId) {
        super(ownerId, 204, "Elvish Branchbender", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Until end of turn, target Forest becomes an X/X Treefolk creature in addition to its other types, where X is the number of Elves you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElvishBranchbenderEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public ElvishBranchbender(final ElvishBranchbender card) {
        super(card);
    }

    @Override
    public ElvishBranchbender copy() {
        return new ElvishBranchbender(this);
    }
}

class ElvishBranchbenderEffect extends OneShotEffect {
    
    final static FilterControlledPermanent filter = new FilterControlledPermanent("Elves you control");
    static {
        filter.add(new SubtypePredicate("Elf"));
    }
    
    ElvishBranchbenderEffect() {
        super(Outcome.Benefit);
        this.staticText = "Until end of turn, target Forest becomes an X/X Treefolk creature in addition to its other types, where X is the number of Elves you control";
    }
    
    ElvishBranchbenderEffect(final ElvishBranchbenderEffect effect) {
        super(effect);
    }
    
    @Override
    public ElvishBranchbenderEffect copy() {
        return new ElvishBranchbenderEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        ContinuousEffect effect = new BecomesCreatureTargetEffect(new ElvishBranchbenderToken(xValue), false, false, Duration.EndOfTurn);
        effect.setTargetPointer(targetPointer);
        game.addEffect(effect, source);
        return false;
    }
}

class ElvishBranchbenderToken extends Token {

    ElvishBranchbenderToken(int xValue) {
        super("Treefolk", "X/X Treefolk creature in addition to its other types, where X is the number of Elves you control");
        cardType.add(CardType.CREATURE);
        subtype.add("Treefolk");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }
}