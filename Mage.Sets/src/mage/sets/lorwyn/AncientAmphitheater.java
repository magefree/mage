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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class AncientAmphitheater extends CardImpl<AncientAmphitheater> {

    private static final FilterCard filter = new FilterCard("a Giant from your hand");
    static {
        filter.add(new SubtypePredicate("Giant"));
    }

    public AncientAmphitheater(UUID ownerId) {
        super(ownerId, 266, "Ancient Amphitheater", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "LRW";

        // As Ancient Amphitheater enters the battlefield, you may reveal a Giant card from your hand. If you don't, Ancient Amphitheater enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Giant card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {R} or {W} to your mana pool.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        
    }

    public AncientAmphitheater(final AncientAmphitheater card) {
        super(card);
    }

    @Override
    public AncientAmphitheater copy() {
        return new AncientAmphitheater(this);
    }
}
