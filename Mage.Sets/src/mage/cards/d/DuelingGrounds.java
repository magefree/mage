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
package mage.cards.d;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 *
 * @author Quercitron
 */
public class DuelingGrounds extends CardImpl {

    public DuelingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        // No more than one creature can attack each turn.
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new NoMoreThanOneCreatureCanAttackEachTurnEffect()),
                new AttackedThisTurnWatcher());

        // No more than one creature can block each turn.
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new NoMoreThanOneCreatureCanBlockEachTurnEffect()),
                new BlockedThisTurnWatcher());
    }

    public DuelingGrounds(final DuelingGrounds card) {
        super(card);
    }

    @Override
    public DuelingGrounds copy() {
        return new DuelingGrounds(this);
    }
}

class NoMoreThanOneCreatureCanAttackEachTurnEffect extends RestrictionEffect {

    public NoMoreThanOneCreatureCanAttackEachTurnEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public NoMoreThanOneCreatureCanAttackEachTurnEffect(final NoMoreThanOneCreatureCanAttackEachTurnEffect effect) {
        super(effect);
        this.staticText = "No more than one creature can attack each turn";
    }

    @Override
    public ContinuousEffect copy() {
        return new NoMoreThanOneCreatureCanAttackEachTurnEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        if (!game.getCombat().getAttackers().isEmpty()) {
            return false;
        }
        AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getName());
        Set<MageObjectReference> attackedThisTurnCreatures = watcher.getAttackedThisTurnCreatures();
        return attackedThisTurnCreatures.isEmpty()
                || (attackedThisTurnCreatures.size() == 1 && attackedThisTurnCreatures.contains(new MageObjectReference(attacker, game)));
    }

}

class NoMoreThanOneCreatureCanBlockEachTurnEffect extends RestrictionEffect {

    public NoMoreThanOneCreatureCanBlockEachTurnEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "No more than one creature can block each turn";
    }

    public NoMoreThanOneCreatureCanBlockEachTurnEffect(final NoMoreThanOneCreatureCanBlockEachTurnEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new NoMoreThanOneCreatureCanBlockEachTurnEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (!game.getCombat().getBlockers().isEmpty()) {
            return false;
        }
        BlockedThisTurnWatcher watcher = (BlockedThisTurnWatcher) game.getState().getWatchers().get(BlockedThisTurnWatcher.class.getSimpleName());
        Set<MageObjectReference> blockedThisTurnCreatures = watcher.getBlockedThisTurnCreatures();
        MageObjectReference blockerReference = new MageObjectReference(blocker.getId(), blocker.getZoneChangeCounter(game), game);
        return blockedThisTurnCreatures.isEmpty()
                || (blockedThisTurnCreatures.size() == 1 && blockedThisTurnCreatures.contains(blockerReference));
    }

}
