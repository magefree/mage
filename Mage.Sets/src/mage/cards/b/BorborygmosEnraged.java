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
package mage.cards.b;

import java.util.UUID;

import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class BorborygmosEnraged extends CardImpl {

    public BorborygmosEnraged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{G}{G}");
        this.subtype.add("Cyclops");

        this.supertype.add("Legendary");

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        //Whenever Borborygmous Enraged deals combat damage to a player, reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RevealLibraryPutIntoHandEffect(3, new FilterLandCard(), Zone.GRAVEYARD), false, false));

        //Discard a land card: Borborygmos Enraged deals 3 damage to target creature or player
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public BorborygmosEnraged(final BorborygmosEnraged card) {
        super(card);
    }

    @Override
    public BorborygmosEnraged copy() {
        return new BorborygmosEnraged(this);
    }
}
