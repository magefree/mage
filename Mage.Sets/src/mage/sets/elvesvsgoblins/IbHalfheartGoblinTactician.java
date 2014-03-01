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
package mage.sets.elvesvsgoblins;

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
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
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
public class IbHalfheartGoblinTactician extends CardImpl<IbHalfheartGoblinTactician> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("two Mountains");
    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent("another Goblin you control");

    static {
        filter.add(new SubtypePredicate("Mountain"));
        filterGoblin.add(new SubtypePredicate("Goblin"));
        filterGoblin.add(new AnotherPredicate());
        filterGoblin.add(new ControllerPredicate(TargetController.YOU));
    }

    public IbHalfheartGoblinTactician(UUID ownerId) {
        super(ownerId, 43, "Ib Halfheart, Goblin Tactician", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "EVG";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Advisor");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever another Goblin you control becomes blocked, sacrifice it. If you do, it deals 4 damage to each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(new IbHalfheartGoblinTacticianEffect(), false, filterGoblin, true));

        // Sacrifice two Mountains: Put two 1/1 red Goblin creature tokens onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new GoblinToken("EVG"), 2),
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

class IbHalfheartGoblinTacticianEffect extends OneShotEffect<IbHalfheartGoblinTacticianEffect> {

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
        if (blockedCreature.sacrifice(source.getSourceId(), game)) {
            Set<UUID> blockingCreatures = new HashSet<>();
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getAttackers().contains(blockedCreature.getId())) {
                    blockingCreatures.addAll(combatGroup.getBlockers());
                }
            }
            for (UUID blockerId : blockingCreatures) {
                Permanent blockingCreature = game.getPermanent(blockerId);
                if (blockingCreature != null) {
                    blockingCreature.damage(4, blockedCreature.getId(), game, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
