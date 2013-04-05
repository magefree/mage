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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author Plopman
 */
public class GoblinAssault extends CardImpl<GoblinAssault> {

    public GoblinAssault(UUID ownerId) {
        super(ownerId, 101, "Goblin Assault", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);

        // At the beginning of your upkeep, put a 1/1 red Goblin creature token with haste onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new GoblinToken()), Constants.TargetController.YOU, false));
        // Goblin creatures attack each turn if able.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GoblinAssaultEffect()));
    }

    public GoblinAssault(final GoblinAssault card) {
        super(card);
    }

    @Override
    public GoblinAssault copy() {
        return new GoblinAssault(this);
    }
}


class GoblinAssaultEffect extends RequirementEffect<GoblinAssaultEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Goblin creatures");
    static {
        filter.add(new SubtypePredicate("Goblin"));
    }
    
    public GoblinAssaultEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Goblin creatures attack each turn if able";
    }

    public GoblinAssaultEffect(final GoblinAssaultEffect effect) {
        super(effect);
    }

    @Override
    public GoblinAssaultEffect copy() {
        return new GoblinAssaultEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (filter.match(permanent, source.getId(), source.getControllerId(), game)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}