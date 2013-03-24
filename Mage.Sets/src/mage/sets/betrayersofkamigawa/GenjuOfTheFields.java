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
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;



/**
 *
 * @author LevelX2
 */
public class GenjuOfTheFields extends CardImpl<GenjuOfTheFields> {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains");
   
    static {
        filter.add(new SubtypePredicate("Plains"));
    }
    public GenjuOfTheFields(UUID ownerId) {
        super(ownerId, 5, "Genju of the Fields", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant Plains
        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {2}: Until end of turn, enchanted Plains becomes a 2/5 white Spirit creature with "Whenever this creature deals damage, its controller gains that much life." It's still a land.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(new SpiritToken(), "Until end of turn, enchanted Plains becomes a 2/5 white Spirit creature with \"Whenever this creature deals damage, its controller gains that much life.\".  It's still a land", Constants.Duration.EndOfTurn),new GenericManaCost(2));
        this.addAbility(ability2);

        // When enchanted Plains is put into a graveyard, you may return Genju of the Fields from your graveyard to your hand.
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandSourceEffect(), "enchanted Plains", true, false);
        this.addAbility(ability3);
    }

    public GenjuOfTheFields(final GenjuOfTheFields card) {
        super(card);
    }

    @Override
    public GenjuOfTheFields copy() {
        return new GenjuOfTheFields(this);
    }

    private class SpiritToken extends Token {
        SpiritToken() {
            super("Spirit", "2/5 white Spirit creature with with \"Whenever this creature deals damage, its controller gains that much life.\"");
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            subtype.add("Spirit");
            power = new MageInt(2);
            toughness = new MageInt(5);
            addAbility(LifelinkAbility.getInstance());
        }
    }
}
