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
package mage.sets.planeshift;

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
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class MagnigothTreefolk extends CardImpl {

    public MagnigothTreefolk(UUID ownerId) {
        super(ownerId, 82, "Magnigoth Treefolk", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Treefolk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Domain - For each basic land type among lands you control, Magnigoth Treefolk has landwalk of that type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MagnigothTreefolkEffect()));
    }

    public MagnigothTreefolk(final MagnigothTreefolk card) {
        super(card);
    }

    @Override
    public MagnigothTreefolk copy() {
        return new MagnigothTreefolk(this);
    }
}

class MagnigothTreefolkEffect extends RestrictionEffect {

    private static final FilterLandPermanent filterPlains = new FilterLandPermanent("Plains", "Plains");
    private static final FilterLandPermanent filterIsland = new FilterLandPermanent("Island", "Island");
    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("Swamp", "Swamp");
    private static final FilterLandPermanent filterMountain = new FilterLandPermanent("Mountain", "Mountain");
    private static final FilterLandPermanent filterForest = new FilterLandPermanent("Forest", "Forest");

    MagnigothTreefolkEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Domain &mdash; For each basic land type among lands you control, {this} has landwalk of that type";
    }

    MagnigothTreefolkEffect(final MagnigothTreefolkEffect effect) {
        super(effect);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !((game.getBattlefield().contains(filterPlains, blocker.getControllerId(), 1, game) 
                    && game.getBattlefield().contains(filterPlains, source.getControllerId(), 1, game))
                || (game.getBattlefield().contains(filterIsland, blocker.getControllerId(), 1, game) 
                    && game.getBattlefield().contains(filterIsland, source.getControllerId(), 1, game))
                || (game.getBattlefield().contains(filterSwamp, blocker.getControllerId(), 1, game) 
                    && game.getBattlefield().contains(filterSwamp, source.getControllerId(), 1, game))
                || (game.getBattlefield().contains(filterMountain, blocker.getControllerId(), 1, game) 
                    && game.getBattlefield().contains(filterMountain, source.getControllerId(), 1, game))
                || (game.getBattlefield().contains(filterForest, blocker.getControllerId(), 1, game) 
                    && game.getBattlefield().contains(filterForest, source.getControllerId(), 1, game)));
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public MagnigothTreefolkEffect copy() {
        return new MagnigothTreefolkEffect(this);
    }
}
