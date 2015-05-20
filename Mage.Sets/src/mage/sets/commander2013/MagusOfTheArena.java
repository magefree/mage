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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsChoicePermanent;

/**
 *
 * @author LevelX2
 */
public class MagusOfTheArena extends CardImpl {

    public MagusOfTheArena(UUID ownerId) {
        super(ownerId, 115, "Magus of the Arena", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "C13";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {3}, {tap}: Tap target creature you control and target creature of an opponent's choice he or she controls. Those creatures fight each other.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MagusOfTheArenaEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetOpponentsChoicePermanent(new FilterControlledCreaturePermanent()));
        this.addAbility(ability);
    }

    public MagusOfTheArena(final MagusOfTheArena card) {
        super(card);
    }

    @Override
    public MagusOfTheArena copy() {
        return new MagusOfTheArena(this);
    }
}

class MagusOfTheArenaEffect extends OneShotEffect {

    MagusOfTheArenaEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap target creature you control and target creature of an opponent's choice he or she controls. Those creatures fight each other";
    }

    MagusOfTheArenaEffect(final MagusOfTheArenaEffect effect) {
        super(effect);
    }

    @Override
    public MagusOfTheArenaEffect copy() {
        return new MagusOfTheArenaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.tap(game);
        }
        creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.tap(game);
        }
        return new FightTargetsEffect().apply(game, source);
    }
}
