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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author JotaPeRL
 */
public class VigeanHydropon extends CardImpl {

    public VigeanHydropon(UUID ownerId) {
        super(ownerId, 135, "Vigean Hydropon", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Plant");
        this.subtype.add("Mutant");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 5
        this.addAbility(new GraftAbility(this, 5));
        
        // Vigean Hydropon can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VigeanHydroponEffect()));
    }

    public VigeanHydropon(final VigeanHydropon card) {
        super(card);
    }

    @Override
    public VigeanHydropon copy() {
        return new VigeanHydropon(this);
    }
}

class VigeanHydroponEffect extends RestrictionEffect {

    public VigeanHydroponEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block";
    }

    public VigeanHydroponEffect(final VigeanHydroponEffect effect) {
        super(effect);
    }

    @Override
    public VigeanHydroponEffect copy() {
        return new VigeanHydroponEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }
}