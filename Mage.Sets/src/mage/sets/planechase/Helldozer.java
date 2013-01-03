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
package mage.sets.planechase;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Helldozer extends CardImpl<Helldozer> {

    public Helldozer(UUID ownerId) {
        super(ownerId, 30, "Helldozer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.expansionSetCode = "HOP";
        this.subtype.add("Zombie");
        this.subtype.add("Giant");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // {B}{B}{B}, {tap}: Destroy target land. If that land was nonbasic, untap Helldozer.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new HelldozerEffect(), new ManaCostsImpl("{B}{B}{B}"));
        ability.addTarget(new TargetLandPermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public Helldozer(final Helldozer card) {
        super(card);
    }

    @Override
    public Helldozer copy() {
        return new Helldozer(this);
    }
}

class HelldozerEffect extends OneShotEffect<HelldozerEffect> {

    public HelldozerEffect() {
        super(Constants.Outcome.DestroyPermanent);
        this.staticText = "Destroy target land. If that land was nonbasic, untap Helldozer";
    }

    public HelldozerEffect(final HelldozerEffect effect) {
        super(effect);
    }

    @Override
    public HelldozerEffect copy() {
        return new HelldozerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent helldozer = game.getPermanent(source.getSourceId());
        Permanent landTarget = game.getPermanent(source.getFirstTarget());
        if (landTarget != null) {
            landTarget.destroy(id, game, false);
        }
        Permanent landPermanent = (Permanent) game.getLastKnownInformation(landTarget.getId(), Zone.BATTLEFIELD);
        if (landPermanent != null
                && !landPermanent.getSupertype().contains("Basic")
                && helldozer != null) {
            return helldozer.untap(game);
        }
        return false;
    }
}