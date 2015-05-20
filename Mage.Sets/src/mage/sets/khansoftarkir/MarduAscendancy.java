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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author LevelX2
 */
public class MarduAscendancy extends CardImpl {

    private static final FilterControlledCreaturePermanent attackFilter = new FilterControlledCreaturePermanent("nontoken creature you control");
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        attackFilter.add(Predicates.not(new TokenPredicate()));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MarduAscendancy(UUID ownerId) {
        super(ownerId, 185, "Mardu Ascendancy", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}{B}");
        this.expansionSetCode = "KTK";


        // Whenever a nontoken creature you control attacks, put a 1/1 red Goblin creature token onto the battlefield tapped and attacking.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new CreateTokenEffect(new GoblinToken(expansionSetCode), 1, true, true), false, attackFilter));

        // Sacrifice Mardu Ascendancy: Creatures you control get +0/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(0, 3, Duration.EndOfTurn, filter, false),
                new SacrificeSourceCost()));
    }

    public MarduAscendancy(final MarduAscendancy card) {
        super(card);
    }

    @Override
    public MarduAscendancy copy() {
        return new MarduAscendancy(this);
    }
}
