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
package mage.cards.i;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class IbHalfheartGoblinTactician extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("two Mountains");
    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent("another Goblin you control");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
        filterGoblin.add(new SubtypePredicate(SubType.GOBLIN));
        filterGoblin.add(new AnotherPredicate());
        filterGoblin.add(new ControllerPredicate(TargetController.YOU));
    }

    public IbHalfheartGoblinTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Goblin");
        this.subtype.add("Advisor");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever another Goblin you control becomes blocked, sacrifice it. If you do, it deals 4 damage to each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(new IbHalfheartGoblinTacticianEffect(), false, filterGoblin, true));

        // Sacrifice two Mountains: Create two 1/1 red Goblin creature tokens.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new GoblinToken(), 2),
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));

    }

    public IbHalfheartGoblinTactician(final IbHalfheartGoblinTactician card) {
        super(card);
    }

    @Override
    public IbHalfheartGoblinTactician copy() {
        return new IbHalfheartGoblinTactician(this);
    }
}

class IbHalfheartGoblinTacticianEffect extends OneShotEffect {

    public IbHalfheartGoblinTacticianEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice it. If you do, it deals 4 damage to each creature blocking it";
    }

    public IbHalfheartGoblinTacticianEffect(final IbHalfheartGoblinTacticianEffect effect) {
        super(effect);
    }

    @Override
    public IbHalfheartGoblinTacticianEffect copy() {
        return new IbHalfheartGoblinTacticianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockedCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (blockedCreature == null) {
            // it can't be sacrificed, nothing happens
            return true;
        }
        Set<UUID> blockingCreatures = new HashSet<>();
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(blockedCreature.getId())) {
                blockingCreatures.addAll(combatGroup.getBlockers());
            }
        }
        if (blockedCreature.sacrifice(source.getSourceId(), game)) {
            for (UUID blockerId : blockingCreatures) {
                Permanent blockingCreature = game.getPermanent(blockerId);
                if (blockingCreature != null) {
                    blockingCreature.damage(4, blockedCreature.getId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
