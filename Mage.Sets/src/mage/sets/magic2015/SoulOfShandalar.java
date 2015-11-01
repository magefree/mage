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
package mage.sets.magic2015;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 * @author noxx
 */
public class SoulOfShandalar extends CardImpl {

    public SoulOfShandalar(UUID ownerId) {
        super(ownerId, 163, "Soul of Shandalar", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "M15";
        this.subtype.add("Avatar");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {3}{R}{R}: Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulOfShandalarEffect(), new ManaCostsImpl("{3}{R}{R}"));
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new SoulOfShandalarTarget());
        this.addAbility(ability);

        // {3}{R}{R}, Exile Soul of Shandalar from your graveyard: Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new SoulOfShandalarEffect(), new ManaCostsImpl("{3}{R}{R}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new SoulOfShandalarTarget());
        this.addAbility(ability);
    }

    public SoulOfShandalar(final SoulOfShandalar card) {
        super(card);
    }

    @Override
    public SoulOfShandalar copy() {
        return new SoulOfShandalar(this);
    }
}

class SoulOfShandalarEffect extends OneShotEffect {

    public SoulOfShandalarEffect() {
        super(Outcome.Damage);
        staticText = "Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls";
    }

    public SoulOfShandalarEffect(final SoulOfShandalarEffect effect) {
        super(effect);
    }

    @Override
    public SoulOfShandalarEffect copy() {
        return new SoulOfShandalarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (player != null) {
            player.damage(3, source.getSourceId(), game, false, true);
        }
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.damage(3, source.getSourceId(), game, false, true);
        }
        return true;
    }
}

class SoulOfShandalarTarget extends TargetPermanent {

    public SoulOfShandalarTarget() {
        super(0, 1, new FilterCreaturePermanent("creature that the targeted player controls"), false);
    }

    public SoulOfShandalarTarget(final SoulOfShandalarTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = source.getFirstTarget();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.getControllerId().equals(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(sourceId);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(sourceId)) {
                object = item;
            }
            if (item.getSourceId().equals(sourceId)) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.getControllerId().equals(playerId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public SoulOfShandalarTarget copy() {
        return new SoulOfShandalarTarget(this);
    }
}
