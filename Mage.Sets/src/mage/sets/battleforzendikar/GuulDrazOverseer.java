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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class GuulDrazOverseer extends CardImpl {

    public GuulDrazOverseer(UUID ownerId) {
        super(ownerId, 112, "Guul Draz Overseer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Vampire");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Landfall</i>- Whenever a land enters the battlefield under your control, other creatures you control get +1/+0 until end of turn. If that land is a Swamp, those creatures get +2/+0 until end of turn instead.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new GuulDrazOverseerEffect(), false, SetTargetPointer.PERMANENT));
    }

    public GuulDrazOverseer(final GuulDrazOverseer card) {
        super(card);
    }

    @Override
    public GuulDrazOverseer copy() {
        return new GuulDrazOverseer(this);
    }
}

class GuulDrazOverseerEffect extends OneShotEffect {

    public GuulDrazOverseerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "other creatures you control get +1/+0 until end of turn. If that land is a Swamp, those creatures get +2/+0 until end of turn instead";
    }

    public GuulDrazOverseerEffect(final GuulDrazOverseerEffect effect) {
        super(effect);
    }

    @Override
    public GuulDrazOverseerEffect copy() {
        return new GuulDrazOverseerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (controller != null && land != null) {
            int boost = 1;
            if (land.getSubtype().contains("Swamp")) {
                boost = 2;
            }
            game.addEffect(new BoostControlledEffect(boost, 0, Duration.EndOfTurn, true), source);
            return true;
        }
        return false;
    }
}
