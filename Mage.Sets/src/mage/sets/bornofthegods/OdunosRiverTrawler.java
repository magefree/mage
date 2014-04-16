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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class OdunosRiverTrawler extends CardImpl<OdunosRiverTrawler> {

    private static final FilterCard filter = new FilterCard("enchantment creature card from your graveyard");
    
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }
    
    public OdunosRiverTrawler(UUID ownerId) {
        super(ownerId, 79, "Odunos River Trawler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Odunos River Trawler enters the battlefield, return target enchantment creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter, true));        
        this.addAbility(ability);
        // {W}, Sacrifice Odunos River Trawler: Return target enchantment creature card from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{W}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter, true));        
        this.addAbility(ability);
    }

    public OdunosRiverTrawler(final OdunosRiverTrawler card) {
        super(card);
    }

    @Override
    public OdunosRiverTrawler copy() {
        return new OdunosRiverTrawler(this);
    }
}
