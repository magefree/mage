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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEquippedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class MageSlayer extends CardImpl<MageSlayer> {

    public MageSlayer(UUID ownerId) {
        super(ownerId, 57, "Mage Slayer", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}{R}{G}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Equipment");

        this.color.setRed(true);
        this.color.setGreen(true);

        // Whenever equipped creature attacks, it deals damage equal to its power to defending player.
        this.addAbility(new AttacksEquippedTriggeredAbility(new MageSlayerEffect(), false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(3), new TargetControlledCreaturePermanent()));

    }

    public MageSlayer(final MageSlayer card) {
        super(card);
    }

    @Override
    public MageSlayer copy() {
        return new MageSlayer(this);
    }
}

class MageSlayerEffect extends OneShotEffect<MageSlayerEffect> {

    public MageSlayerEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to its power to defending player";
    }

    public MageSlayerEffect(final MageSlayerEffect effect) {
        super(effect);
    }

    @Override
    public MageSlayerEffect copy() {
        return new MageSlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            int power = game.getPermanent(equipment.getAttachedTo()).getPower().getValue();
            UUID defendingPlayerId = game.getCombat().getDefendingPlayer(equipment.getAttachedTo());
            if (defendingPlayerId != null) {
                game.getPlayer(defendingPlayerId).damage(power, id, game, false, true);
                return true;
            }
        }
        return false;
    }
}
