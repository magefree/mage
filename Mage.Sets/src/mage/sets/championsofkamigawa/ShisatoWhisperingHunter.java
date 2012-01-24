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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.SkipNextPlayerUntapStepEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX
 */
public class ShisatoWhisperingHunter extends CardImpl<ShisatoWhisperingHunter> {

    
    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Snake");

    static {
        filter.getSubtype().add("Snake");
        filter.setTargetController(TargetController.YOU);
    }
    
    public ShisatoWhisperingHunter(UUID ownerId) {
        super(ownerId, 242, "Shisato, Whispering Hunter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Snake");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, sacrifice a Snake.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(filter, 1,""), Constants.TargetController.YOU, false));        
        // Whenever Shisato, Whispering Hunter deals combat damage to a player, that player skips his or her next untap step.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SkipNextPlayerUntapStepEffect("that "),false, true));
    }

    public ShisatoWhisperingHunter(final ShisatoWhisperingHunter card) {
        super(card);
    }

    @Override
    public ShisatoWhisperingHunter copy() {
        return new ShisatoWhisperingHunter(this);
    }
}