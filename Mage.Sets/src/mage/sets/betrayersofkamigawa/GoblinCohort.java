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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayerCastCreatureWatcher;

/**
 *
 * @author LevelX2
 */
public class GoblinCohort extends CardImpl {

    public GoblinCohort(UUID ownerId) {
        super(ownerId, 106, "Goblin Cohort", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Goblin Cohort can't attack unless you've cast a creature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinCohortEffect()), new PlayerCastCreatureWatcher());

    }

    public GoblinCohort(final GoblinCohort card) {
        super(card);
    }

    @Override
    public GoblinCohort copy() {
        return new GoblinCohort(this);
    }
}

class GoblinCohortEffect extends RestrictionEffect {

    public GoblinCohortEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you've cast a creature spell this turn";
    }

    public GoblinCohortEffect(final GoblinCohortEffect effect) {
        super(effect);
    }

    @Override
    public GoblinCohortEffect copy() {
        return new GoblinCohortEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            PlayerCastCreatureWatcher watcher = (PlayerCastCreatureWatcher) game.getState().getWatchers().get("PlayerCastCreature");
            if (watcher != null && !watcher.playerDidCastCreatureThisTurn(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}

