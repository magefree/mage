/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.common;


import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Commander;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author Plopman
 */
public class CastCommanderAbility extends ActivatedAbilityImpl<CastCommanderAbility> {

    public CastCommanderAbility(Card card) {
        super(Zone.COMMAND, new CastCommanderEffect(), card.getManaCost());
        this.timing = TimingRule.SORCERY;
        this.usesStack = false;
        this.controllerId = card.getOwnerId();
        this.sourceId = card.getId();
    }

    public CastCommanderAbility(final CastCommanderAbility ability) {
        super(ability);
    }

    @Override
    public CastCommanderAbility copy() {
        return new CastCommanderAbility(this);
    }

}

class CastCommanderEffect extends OneShotEffect<CastCommanderEffect> {

    public CastCommanderEffect() {
        super(Outcome.Benefit);
        staticText = "cast commander";
    }

    public CastCommanderEffect(final CastCommanderEffect effect) {
        super(effect);
    }

    @Override
    public CastCommanderEffect copy() {
        return new CastCommanderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object =  game.getObject(source.getSourceId());
        if (object != null && object instanceof Commander) {
            Commander commander = (Commander)object;
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                SpellAbility spellAbility = commander.getCard().getSpellAbility();

                spellAbility.clear();
                int amount = source.getManaCostsToPay().getX();
                spellAbility.getManaCostsToPay().setX(amount);
                for (Target target : spellAbility.getTargets()) {
                    target.setRequired(true);
                }
                if(controller.cast(spellAbility, game, true)){
                    Integer castCount = (Integer)game.getState().getValue(commander.getId() + "_castCount");
                    if(castCount != null){
                        castCount++;
                        game.getState().setValue(commander.getId() + "_castCount", castCount);
                    }
                    else {
                        castCount = 1;
                        game.getState().setValue(commander.getId() + "_castCount", castCount);
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}