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

package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.common.CantBlockSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Backfir3
 */
public class RampartCrawler extends CardImpl<RampartCrawler> {

    public RampartCrawler(UUID ownerId) {
		super(ownerId, 156, "Rampart Crawler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
		this.expansionSetCode = "MMQ";
		this.subtype.add("Lizard");
		this.subtype.add("Mercenary");
		this.color.setBlack(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);
	
        // Rampart Crawler can't be blocked by Walls.
		this.addAbility(new RampartCrawlerAbility());
    }

    public RampartCrawler(final RampartCrawler card) {
        super(card);
    }

    @Override
    public RampartCrawler copy() {
        return new RampartCrawler(this);
    }

}

class RampartCrawlerAbility extends EvasionAbility<RampartCrawlerAbility> {

    public RampartCrawlerAbility() {
        this.addEffect(new RampartCrawlerEffect());
    }

    public RampartCrawlerAbility(final RampartCrawlerAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "{this} can't be blocked by Walls.";
    }

    @Override
    public RampartCrawlerAbility copy() {
        return new RampartCrawlerAbility(this);
    }

}

class RampartCrawlerEffect extends CantBlockSourceEffect {

    public RampartCrawlerEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public RampartCrawlerEffect(final RampartCrawlerEffect effect) {
        super(effect);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !blocker.hasSubtype("Wall");
    }

    @Override
    public RampartCrawlerEffect copy() {
        return new RampartCrawlerEffect(this);
    }

}