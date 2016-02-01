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
package mage.sets.urzassaga;

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
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author icetc
 */
public class Okk extends CardImpl {

    public Okk(UUID ownerId) {
        super(ownerId, 204, "Okk", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "USG";
        this.subtype.add("Goblin");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Okk can't attack unless a creature with greater power also attacks.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OkkAttackEffect()));

        // Okk can't block unless a creature with greater power also blocks.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OkkBlockEffect()));
    }

    public Okk(final Okk card) {
        super(card);
    }

    @Override
    public Okk copy() {
        return new Okk(this);
    }
}

class OkkAttackEffect extends RestrictionEffect {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("Attacking creatures");

    public OkkAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless a creature with greater power also attacks";
    }

    public OkkAttackEffect(final OkkAttackEffect effect) {
        super(effect);
    }

    @Override
    public OkkAttackEffect copy() {
        return new OkkAttackEffect(this);
    }

    @Override
    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            // Search for an attacking creature with greater power
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (creature.getPower().getValue() > permanent.getPower().getValue()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

class OkkBlockEffect extends RestrictionEffect {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");

    public OkkBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless a creature with greater power also blocks.";
    }

    public OkkBlockEffect(final OkkBlockEffect effect) {
        super(effect);
    }

    @Override
    public OkkBlockEffect copy() {
        return new OkkBlockEffect(this);
    }

    @Override
    public boolean canBlockCheckAfter(Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            // Search for a blocking creature with greater power
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (creature.getPower().getValue() > permanent.getPower().getValue()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
