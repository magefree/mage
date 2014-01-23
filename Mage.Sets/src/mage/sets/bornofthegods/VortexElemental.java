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
package mage.sets.bornofthegods;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class VortexElemental extends CardImpl<VortexElemental> {

    public VortexElemental(UUID ownerId) {
        super(ownerId, 56, "Vortex Elemental", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Elemental");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {U}: Put Vortex Elemental and each creature blocking or blocked by it on top of their owners' libraries, then those players shuffle their libraries.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new VortexElementaöEffect(), new ManaCostsImpl("{U}")));

        // {3}{U}{U}: Target creature blocks Vortex Elemental this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ManaCostsImpl("{3}{U}{U}"));
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }

    public VortexElemental(final VortexElemental card) {
        super(card);
    }

    @Override
    public VortexElemental copy() {
        return new VortexElemental(this);
    }
}

class VortexElementaöEffect extends OneShotEffect<VortexElementaöEffect> {

    public VortexElementaöEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put {this} and each creature blocking or blocked by it on top of their owners' libraries, then those players shuffle their libraries";
    }

    public VortexElementaöEffect(final VortexElementaöEffect effect) {
        super(effect);
    }

    @Override
    public VortexElementaöEffect copy() {
        return new VortexElementaöEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Combat combat = game.getState().getCombat();
            Set<UUID> creaturesToReturn = new HashSet<UUID>();
            Set<UUID> playersToShuffle = new HashSet<UUID>();
            creaturesToReturn.add(source.getSourceId());
            if (combat != null) {
                for(CombatGroup combatGroup: combat.getGroups()) {
                    if (combatGroup.getAttackers().contains(source.getSourceId())) {
                        creaturesToReturn.addAll(combatGroup.getBlockers());
                    } else if (combatGroup.getBlockers().contains(source.getSourceId())) {
                        creaturesToReturn.addAll(combatGroup.getAttackers());
                    }
                }
            }
            for (UUID creatureId: creaturesToReturn) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    playersToShuffle.add(creature.getControllerId());
                    creature.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }
            for (UUID playerId: playersToShuffle){
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.shuffleLibrary(game);
                }
            }

            return true;
        }


        return false;
    }
}
