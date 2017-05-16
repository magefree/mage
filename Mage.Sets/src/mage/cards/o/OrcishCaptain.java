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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class OrcishCaptain extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Orc creature");

    static {
        filter.add(new SubtypePredicate(SubType.ORC));
    }

    public OrcishCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add("Orc");
        this.subtype.add("Warrior");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Flip a coin. If you win the flip, target Orc creature gets +2/+0 until end of turn. If you lose the flip, it gets -0/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrcishCaptainEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public OrcishCaptain(final OrcishCaptain card) {
        super(card);
    }

    @Override
    public OrcishCaptain copy() {
        return new OrcishCaptain(this);
    }
}

class OrcishCaptainEffect extends OneShotEffect {

    public OrcishCaptainEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, target Orc creature gets +2/+0 until end of turn. If you lose the flip, it gets -0/-2 until end of turn";
    }

    public OrcishCaptainEffect(OrcishCaptainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            if (controller.flipCoin(game)) {
                game.informPlayers("Orcish Captain: Won flip. Target Orc creature gets +2/+0 until end of turn.");
                game.addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn), source);
                return true;
            } else {
                game.informPlayers("Orcish Captain: Lost flip. Target Orc creature gets -0/-2 until end of turn.");
                game.addEffect(new BoostTargetEffect(-0, -2, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public OrcishCaptainEffect copy() {
        return new OrcishCaptainEffect(this);
    }
}
