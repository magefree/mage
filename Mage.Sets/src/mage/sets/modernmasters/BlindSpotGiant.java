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
package mage.sets.modernmasters;

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
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class BlindSpotGiant extends CardImpl<BlindSpotGiant> {

    public BlindSpotGiant(UUID ownerId) {
        super(ownerId, 105, "Blind-Spot Giant", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Blind-Spot Giant can't attack or block unless you control another Giant.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlindSpotGiantEffect()));

    }

    public BlindSpotGiant(final BlindSpotGiant card) {
        super(card);
    }

    @Override
    public BlindSpotGiant copy() {
        return new BlindSpotGiant(this);
    }
}

class BlindSpotGiantEffect extends RestrictionEffect<BlindSpotGiantEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Giant");
    static {
        filter.add(new SubtypePredicate("Giant"));
        filter.add(new AnotherPredicate());
    }

    public BlindSpotGiantEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you control another Giant";
    }

    public BlindSpotGiantEffect(final BlindSpotGiantEffect effect) {
        super(effect);
    }

    @Override
    public BlindSpotGiantEffect copy() {
        return new BlindSpotGiantEffect(this);
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
            if (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
                return false;
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}
