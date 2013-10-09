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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BurningSands extends CardImpl<BurningSands> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a land");

    public BurningSands(UUID ownerId) {
        super(ownerId, 180, "Burning Sands", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        this.expansionSetCode = "ODY";

        this.color.setRed(true);

        // Whenever a creature dies, that creature's controller sacrifices a land.
        this.addAbility(new DiesCreatureTriggeredAbility(new SacrificeEffect(filter, 1, "that creature's controller"), false, false, true));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof DiesCreatureTriggeredAbility) {
            UUID creatureId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
            Permanent creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD);
            if (creature != null) {
                ability.getEffects().get(0).setTargetPointer(new FixedTarget(creature.getControllerId()));
            }
        }
    }

    public BurningSands(final BurningSands card) {
        super(card);
    }

    @Override
    public BurningSands copy() {
        return new BurningSands(this);
    }
}
