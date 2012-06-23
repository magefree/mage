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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.CardImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.Ability;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SimpleActivatedAbility;
import mage.Constants.Zone;
import mage.abilities.effects.OneShotEffect;
import mage.Constants.Outcome;
import mage.game.permanent.Permanent;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class HolyJusticiar extends CardImpl<HolyJusticiar> {

    public HolyJusticiar(UUID ownerId) {
        super(ownerId, 25, "Holy Justiciar", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{W}, {tap}: Tap target creature. If that creature is a Zombie, exile it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HolyJusticiarEffect(), new ManaCostsImpl("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public HolyJusticiar(final HolyJusticiar card) {
        super(card);
    }

    @Override
    public HolyJusticiar copy() {
        return new HolyJusticiar(this);
    }
}

class HolyJusticiarEffect extends OneShotEffect<HolyJusticiarEffect> {

    public HolyJusticiarEffect() {
        super(Outcome.Detriment);
        staticText = "Tap target creature.  If that creature is a Zombie, exile it";
    }

    public HolyJusticiarEffect(final HolyJusticiarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            if (creature.hasSubtype("Zombie")) {
                creature.tap(game);
                creature.moveToExile(source.getId(), creature.getName(), source.getSourceId(), game);
            } else {
                creature.tap(game);
            }
            return true;
        }
        return false; 
    }

    @Override
    public HolyJusticiarEffect copy() {
        return new HolyJusticiarEffect(this);
    }
}

