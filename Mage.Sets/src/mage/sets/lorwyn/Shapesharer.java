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
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyApplyToPermanent;


/**
 * @author duncant
 */
public class Shapesharer extends CardImpl {

    private static final FilterPermanent filterShapeshifter = new FilterPermanent("Shapeshifter");

    static {
        filterShapeshifter.add(new SubtypePredicate("Shapeshifter"));
    }

    public Shapesharer(UUID ownerId) {
        super(ownerId, 85, "Shapesharer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(ChangelingAbility.getInstance());
        
        // {2}{U}: Target Shapeshifter becomes a copy of target creature until your next turn.
        Ability copyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                                                         new ShapesharerEffect(),
                                                         new ManaCostsImpl("{2}{U}"));
        copyAbility.addTarget(new TargetPermanent(filterShapeshifter));
        copyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(copyAbility);
    }

    public Shapesharer(final Shapesharer card) {
        super(card);
    }

    @Override
    public Shapesharer copy() {
        return new Shapesharer(this);
    }
}

class ShapesharerEffect extends OneShotEffect {
    public ShapesharerEffect() {
        super(Outcome.Copy);
        this.staticText = "Target Shapeshifter becomes a copy of target creature until your next turn.";
    }

    public ShapesharerEffect(final ShapesharerEffect effect) {
        super(effect);
    }

    @Override
    public ShapesharerEffect copy() {
        return new ShapesharerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Permanent copyTo = game.getPermanent(ability.getFirstTarget());
        if (copyTo != null) {
            Permanent copyFrom = game.getPermanentOrLKIBattlefield(ability.getTargets().get(1).getFirstTarget());
            game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo, ability, new EmptyApplyToPermanent());
        }
        return true;
    }
}
