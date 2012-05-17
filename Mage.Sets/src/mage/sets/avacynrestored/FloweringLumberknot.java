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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class FloweringLumberknot extends CardImpl<FloweringLumberknot> {

    public FloweringLumberknot(UUID ownerId) {
        super(ownerId, 178, "Flowering Lumberknot", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Treefolk");

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flowering Lumberknot can't attack or block unless it's paired with a creature with soulbond.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new FloweringLumberknotEffect()));
    }

    public FloweringLumberknot(final FloweringLumberknot card) {
        super(card);
    }

    @Override
    public FloweringLumberknot copy() {
        return new FloweringLumberknot(this);
    }
}

class FloweringLumberknotEffect extends RestrictionEffect<FloweringLumberknotEffect> {

    public FloweringLumberknotEffect() {
        super(Constants.Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless it's paired with a creature with soulbond";
    }

    public FloweringLumberknotEffect(final FloweringLumberknotEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (permanent.getPairedCard() == null) {
                return true; // not paired => can't attack or block
            }
            Permanent paired = game.getPermanent(permanent.getPairedCard());
            if (paired != null && paired.getAbilities().contains(SoulbondAbility.getInstance())) {
                return false; // paired => can attack or block
            }
            // can't attack or block otherwise
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public FloweringLumberknotEffect copy() {
        return new FloweringLumberknotEffect(this);
    }
}
