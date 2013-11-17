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
package mage.sets.magic2013;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.CastAsThoughItHadFlashEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public class YevaNaturesHerald extends CardImpl<YevaNaturesHerald> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("green creature cards");
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public YevaNaturesHerald(UUID ownerId) {
        super(ownerId, 197, "Yeva, Nature's Herald", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "M13";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // You may cast green creature cards as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashEffect(Duration.WhileOnBattlefield, filter)));
    }

    public YevaNaturesHerald(final YevaNaturesHerald card) {
        super(card);
    }

    @Override
    public YevaNaturesHerald copy() {
        return new YevaNaturesHerald(this);
    }
}
