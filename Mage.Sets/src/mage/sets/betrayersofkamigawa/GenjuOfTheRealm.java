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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continious.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class GenjuOfTheRealm extends CardImpl<GenjuOfTheRealm> {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent();
   
    public GenjuOfTheRealm(UUID ownerId) {
        super(ownerId, 151, "Genju of the Realm", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Aura");

        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.color.setGreen(true);

        // Enchant Land
        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {2}: Enchanted land becomes a legendary 8/12 Spirit creature with trample until end of turn. It's still a land.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(new SpiritToken(), "Enchanted land becomes a legendary 8/12 Spirit creature with trample until end of turn. It's still a land", Constants.Duration.EndOfTurn),new GenericManaCost(2));
        this.addAbility(ability2);

        // When enchanted land is put into a graveyard, you may return Genju of the Realm from your graveyard to your hand.
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandSourceEffect(), "enchanted land", true);
        this.addAbility(ability3);
    }

    public GenjuOfTheRealm(final GenjuOfTheRealm card) {
        super(card);
    }

    @Override
    public GenjuOfTheRealm copy() {
        return new GenjuOfTheRealm(this);
    }

    private class SpiritToken extends Token {
        SpiritToken() {
            super("Spirit", "legendary 8/12 Spirit creature with trample");
            supertype.add("Legendary");
            cardType.add(CardType.CREATURE);
            this.color.setWhite(true);
            this.color.setBlue(true);
            this.color.setBlack(true);
            this.color.setRed(true);
            this.color.setGreen(true);
            subtype.add("Spirit");
            power = new MageInt(8);
            toughness = new MageInt(12);
            this.addAbility(TrampleAbility.getInstance());
        }
    }
}
