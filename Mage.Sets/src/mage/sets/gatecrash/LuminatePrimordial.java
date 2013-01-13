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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class LuminatePrimordial extends CardImpl<LuminatePrimordial> {

    public LuminatePrimordial(UUID ownerId) {
        super(ownerId, 20, "Luminate Primordial", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Avatar");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Luminate Primordial enters the battlefield, for each opponent, exile up to one target creature
        // that player controls and that player gains life equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LuminatePrimordialEffect(),false));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            for(UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    ability.getTargets().clear();
                    FilterCreaturePermanent filter = new FilterCreaturePermanent(new StringBuilder("creature from opponent ").append(opponent.getName()).toString());
                    filter.add(new ControllerIdPredicate(opponentId));
                    TargetCreaturePermanent target = new TargetCreaturePermanent(0,1, filter,false);
                    ability.addTarget(target);
                }
            }
        }
    }

    public LuminatePrimordial(final LuminatePrimordial card) {
        super(card);
    }

    @Override
    public LuminatePrimordial copy() {
        return new LuminatePrimordial(this);
    }
}

class LuminatePrimordialEffect extends OneShotEffect<LuminatePrimordialEffect> {

    public LuminatePrimordialEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each opponent, exile up to one target creature that player controls and that player gains life equal to its power";
    }

    public LuminatePrimordialEffect(final LuminatePrimordialEffect effect) {
        super(effect);
    }

    @Override
    public LuminatePrimordialEffect copy() {
        return new LuminatePrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target: source.getTargets()) {
            if (target instanceof TargetCreaturePermanent) {
                Permanent targetCreature = game.getPermanent(target.getFirstTarget());
                if (targetCreature != null && !targetCreature.getControllerId().equals(source.getControllerId())) {
                    int amountLife = targetCreature.getPower().getValue();
                    Player controller = game.getPlayer(targetCreature.getControllerId());
                    targetCreature.moveToExile(null, null, source.getSourceId(), game);
                    if (controller != null && amountLife != 0) {
                        controller.gainLife(amountLife, game);
                    }
                }
            }
        }
        return true;
    }
}
