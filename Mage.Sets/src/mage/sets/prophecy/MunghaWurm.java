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
package mage.sets.prophecy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MunghaWurm extends CardImpl<MunghaWurm> {

    public MunghaWurm(UUID ownerId) {
        super(ownerId, 119, "Mungha Wurm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "PCY";
        this.subtype.add("Wurm");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // You can't untap more than one land during your untap step.
         this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MunghaWurmEffect()));
    }

    public MunghaWurm(final MunghaWurm card) {
        super(card);
    }

    @Override
    public MunghaWurm copy() {
        return new MunghaWurm(this);
    }
}

class MunghaWurmEffect extends RestrictionUntapNotMoreThanEffect<MunghaWurmEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public MunghaWurmEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "Players can't untap more than one land during their untap steps";
    }

    public MunghaWurmEffect(final MunghaWurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applied to all players
        return true;
    }

    @Override
    public MunghaWurmEffect copy() {
        return new MunghaWurmEffect(this);
    }

}
