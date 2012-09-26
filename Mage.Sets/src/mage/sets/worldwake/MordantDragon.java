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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class MordantDragon extends CardImpl<MordantDragon> {

    public MordantDragon(UUID ownerId) {
        super(ownerId, 85, "Mordant Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{R}: Mordant Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Constants.Duration.EndOfTurn), new ManaCostsImpl("{1}{R}")));

        // Whenever Mordant Dragon deals combat damage to a player, you may have it deal that much damage to target creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MordantDragonEffect(), true, true));
    }

    public MordantDragon(final MordantDragon card) {
        super(card);
    }

    @Override
    public MordantDragon copy() {
        return new MordantDragon(this);
    }
}

class MordantDragonEffect extends OneShotEffect<MordantDragonEffect> {

    public MordantDragonEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to target creature that player controls";
    }

    public MordantDragonEffect(final MordantDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + player.getName() + " controls");
                filter.add(new ControllerIdPredicate(player.getId()));
                TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
                if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Damage, source.getControllerId(), source.getId(), game)) {
                    UUID creature = target.getFirstTarget();
                    if (creature != null) {
                        game.getPermanent(creature).damage(amount, source.getSourceId(), game, true, false);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public MordantDragonEffect copy() {
        return new MordantDragonEffect(this);
    }
}
