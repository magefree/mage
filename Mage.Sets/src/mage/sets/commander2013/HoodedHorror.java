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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HoodedHorror extends CardImpl<HoodedHorror> {

    public HoodedHorror(UUID ownerId) {
        super(ownerId, 80, "Hooded Horror", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "C13";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Hooded Horror can't be blocked if defending player controls the most creatures or is tied for the most.
      this.addAbility(new SimpleEvasionAbility(new HoodedHorrorCantBeBlockedEffect()));
    }

    public HoodedHorror(final HoodedHorror card) {
        super(card);
    }

    @Override
    public HoodedHorror copy() {
        return new HoodedHorror(this);
    }
}

class HoodedHorrorCantBeBlockedEffect extends RestrictionEffect<HoodedHorrorCantBeBlockedEffect> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HoodedHorrorCantBeBlockedEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't be blocked if defending player controls the most creatures or is tied for the most";
    }

    public HoodedHorrorCantBeBlockedEffect(final HoodedHorrorCantBeBlockedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId()) && permanent.isAttacking()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int maxCreatures = 0;
            UUID playerIdWithMax = null;
            for (UUID playerId : controller.getInRange()) {
                int creatures = game.getBattlefield().countAll(filter, playerId, game);
                if (creatures > maxCreatures || (creatures == maxCreatures && playerId.equals(blocker.getControllerId())) ) {
                    maxCreatures = creatures;
                    playerIdWithMax = playerId;
                }
            }
            if (playerIdWithMax != null && playerIdWithMax.equals(blocker.getControllerId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public HoodedHorrorCantBeBlockedEffect copy() {
        return new HoodedHorrorCantBeBlockedEffect(this);
    }
}
