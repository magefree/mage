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
package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class HarborSerpent extends CardImpl<HarborSerpent> {

    public HarborSerpent(UUID ownerId) {
        super(ownerId, 56, "Harbor Serpent", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "M11";
        this.subtype.add("Serpent");
        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new IslandwalkAbility());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HarborSerpentEffect()));
    }

    public HarborSerpent(final HarborSerpent card) {
        super(card);
    }

    @Override
    public HarborSerpent copy() {
        return new HarborSerpent(this);
    }
}

class HarborSerpentEffect extends RestrictionEffect<HarborSerpentEffect> {

    private final FilterLandPermanent filter = new FilterLandPermanent("Island");

    public HarborSerpentEffect() {
        super(Duration.WhileOnBattlefield);
        filter.getSubtype().add("Island");
        staticText = "{this} can't attack unless there are five or more Islands on the battlefield";
    }

    public HarborSerpentEffect(final HarborSerpentEffect effect) {
        super(effect);
    }

    @Override
    public HarborSerpentEffect copy() {
        return new HarborSerpentEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getBattlefield().countAll(filter) < 5) {
            return true;
        }
        return false;
    }
}
