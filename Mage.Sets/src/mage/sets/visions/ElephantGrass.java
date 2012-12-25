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
package mage.sets.visions;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class ElephantGrass extends CardImpl<ElephantGrass> {

    public ElephantGrass(UUID ownerId) {
        super(ownerId, 54, "Elephant Grass", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "VIS";

        this.color.setGreen(true);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));
        // Black creatures can't attack you.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ElephantGrassReplacementEffect()));
        // Nonblack creatures can't attack you unless their controller pays {2} for each creature he or she controls that's attacking you.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ElephantGrassReplacementEffect2()));
    }

    public ElephantGrass(final ElephantGrass card) {
        super(card);
    }

    @Override
    public ElephantGrass copy() {
        return new ElephantGrass(this);
    }
}


class ElephantGrassReplacementEffect extends ReplacementEffectImpl<ElephantGrassReplacementEffect> {

   
    ElephantGrassReplacementEffect ( ) {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Neutral);
        staticText = "Black creatures can't attack you";
    }

    ElephantGrassReplacementEffect ( ElephantGrassReplacementEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER && event.getTargetId().equals(source.getControllerId()) ) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if(creature != null && creature.getColor().isBlack()){
                return true;
            }
        }
        return false;
    }

    @Override
    public ElephantGrassReplacementEffect copy() {
        return new ElephantGrassReplacementEffect(this);
    }

}

class ElephantGrassReplacementEffect2 extends ReplacementEffectImpl<ElephantGrassReplacementEffect2> {

    ElephantGrassReplacementEffect2 ( ) {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Neutral);
        staticText = "Nonblack creatures can't attack you unless their controller pays {2} for each creature he or she controls that's attacking you";
    }

    ElephantGrassReplacementEffect2 ( ElephantGrassReplacementEffect2 effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            Player player = game.getPlayer(event.getPlayerId());
            if ( player != null && event.getTargetId().equals(source.getControllerId())) {
                ManaCostsImpl cost = new ManaCostsImpl("{2}");
                if ( cost.canPay(source.getSourceId(), event.getPlayerId(), game) &&
                     player.chooseUse(Constants.Outcome.Benefit, "Pay {2} to declare attacker?", game) ) {
                    if (cost.payOrRollback(source, game, this.getId(), event.getPlayerId())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER && event.getTargetId().equals(source.getControllerId()) ) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if(creature != null && !creature.getColor().isBlack()){
                return true;
            }
        }
        return false;
    }

    @Override
    public ElephantGrassReplacementEffect2 copy() {
        return new ElephantGrassReplacementEffect2(this);
    }

}