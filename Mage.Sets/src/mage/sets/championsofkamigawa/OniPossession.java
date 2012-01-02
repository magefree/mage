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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continious.SetCardSubtypeAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public class OniPossession extends CardImpl<OniPossession> {

    private static final List<String> setSubtypes = new ArrayList<String>();
    static {
        setSubtypes.add("Demon");
        setSubtypes.add("Spirit");
    }
    
    public OniPossession(UUID ownerId) {
        super(ownerId, 135, "Oni Possession", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Aura");

        this.color.setBlack(true);
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
	this.getSpellAbility().addTarget(auraTarget);
	this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.BoostCreature));
	Ability ability = new EnchantAbility(auraTarget.getTargetName());
	this.addAbility(ability);
        // At the beginning of your upkeep, sacrifice a creature.
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(new SacrificeTargetEffect("sacrifice a creature"), Constants.TargetController.YOU, false);
        ability2.addTarget(new TargetControlledCreaturePermanent(1,1, new FilterControlledCreaturePermanent(),false, true));
        this.addAbility(ability2);
        // Enchanted creature gets +3/+3 and has trample.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Constants.Duration.WhileOnBattlefield)));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAttachedEffect(TrampleAbility.getInstance(), Constants.AttachmentType.AURA)));
        // Enchanted creature is a Demon Spirit.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SetCardSubtypeAttachedEffect(setSubtypes, Constants.Duration.WhileOnBattlefield, Constants.AttachmentType.AURA)));
    }

    public OniPossession(final OniPossession card) {
        super(card);
    }

    @Override
    public OniPossession copy() {
        return new OniPossession(this);
    }
}
