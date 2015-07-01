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
package mage.sets.mastersedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public class CuombajjWitches extends CardImpl {

    private final UUID originalId;

    public CuombajjWitches(UUID ownerId) {
        super(ownerId, 65, "Cuombajj Witches", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "MED";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Cuombajj Witches deals 1 damage to target creature or player and 1 damage to target creature or player of an opponent's choice.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to target creature or player and 1 damage to target creature or player of an opponent's choice");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        originalId = ability.getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability.getOriginalId().equals(originalId)) {
            Player controller = game.getPlayer(ability.getControllerId());
            if(controller != null) {
                UUID opponentId = null;
                if(game.getOpponents(controller.getId()).size() > 1) {
                    Target target = new TargetOpponent(true);
                    if(controller.chooseTarget(Outcome.Neutral, target, ability, game)) {
                        opponentId = target.getFirstTarget();
                    }
                }
                else {
                    opponentId = game.getOpponents(controller.getId()).iterator().next();
                }

                if(opponentId != null) {
                    ability.getTargets().get(1).setTargetController(opponentId);
                }
            }
        }
    }

    public CuombajjWitches(final CuombajjWitches card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public CuombajjWitches copy() {
        return new CuombajjWitches(this);
    }
}
