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
package mage.sets.bornofthegods;

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
public class FelhideBrawler extends CardImpl<FelhideBrawler> {

    public FelhideBrawler(UUID ownerId) {
        super(ownerId, 70, "Felhide Brawler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Minotaur");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Felhide Brawler can't block unless you control another Minotaur.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FelhideBrawlerRestrictionEffect()));
    }

    public FelhideBrawler(final FelhideBrawler card) {
        super(card);
    }

    @Override
    public FelhideBrawler copy() {
        return new FelhideBrawler(this);
    }
}

class FelhideBrawlerRestrictionEffect extends RestrictionEffect<FelhideBrawlerRestrictionEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Minotaur");

    static {
        filter.add(new SubtypePredicate("Minotaur"));
        filter.add(new AnotherPredicate());
    }

    public FelhideBrawlerRestrictionEffect() {
        super(Duration.WhileOnBattlefield);

        staticText = "{this} can't block unless you control another Minotaur";
    }

    public FelhideBrawlerRestrictionEffect(final FelhideBrawlerRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public FelhideBrawlerRestrictionEffect copy() {
        return new FelhideBrawlerRestrictionEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getBattlefield().countAll(filter, source.getControllerId(), game) == 0) {
            return true;
        }
        return false;
    }
}
