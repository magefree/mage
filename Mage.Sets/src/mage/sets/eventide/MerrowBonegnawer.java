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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class MerrowBonegnawer extends CardImpl<MerrowBonegnawer> {
    
    private UUID exileId = UUID.randomUUID();
    
    private static final FilterSpell filter = new FilterSpell("black spell");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public MerrowBonegnawer(UUID ownerId) {
        super(ownerId, 37, "Merrow Bonegnawer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Merfolk");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player exiles a card from his or her graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileFromZoneTargetEffect(Zone.GRAVEYARD, exileId, "Merrow Bonegnawer", new FilterCard("a card")), new TapSourceCost());
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        
        // Whenever you cast a black spell, you may untap Merrow Bonegnawer.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), filter, true, false));
        
    }

    public MerrowBonegnawer(final MerrowBonegnawer card) {
        super(card);
    }

    @Override
    public MerrowBonegnawer copy() {
        return new MerrowBonegnawer(this);
    }
}
