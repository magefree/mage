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
package mage.sets.homelands;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class DrudgeSpell extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Skeleton tokens");
    
    static {
        filter.add(new SubtypePredicate("Skeleton"));
        filter.add(new TokenPredicate());
    }

    public DrudgeSpell(UUID ownerId) {
        super(ownerId, 6, "Drudge Spell", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");
        this.expansionSetCode = "HML";

        // {B}, Exile two creature cards from your graveyard: Put a 1/1 black Skeleton creature token onto the battlefield. It has "{B}: Regenerate this creature."
        Effect effect = new CreateTokenEffect(new SkeletonToken());
        effect.setText("Put a 1/1 black Skeleton creature token onto the battlefield. It has \"{B}: Regenerate this creature.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, 2, new FilterCreatureCard("creature cards from your graveyard"))));
        this.addAbility(ability);
        
        // When Drudge Spell leaves the battlefield, destroy all Skeleton tokens. They can't be regenerated.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DestroyAllEffect(filter, true), false));
    }

    public DrudgeSpell(final DrudgeSpell card) {
        super(card);
    }

    @Override
    public DrudgeSpell copy() {
        return new DrudgeSpell(this);
    }
}

class SkeletonToken extends Token {

    SkeletonToken() {
        super("Skeleton", "1/1 black Skeleton creature token onto the battlefield. It has \"{B}: Regenerate this creature.\"");
        this.setOriginalExpansionSetCode("HML");
        this.getPower().initValue(1);
        this.getToughness().initValue(1);
        this.color.setBlack(true);
        this.getSubtype().add("Skeleton");
        this.getCardType().add(CardType.CREATURE);
        
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}")));
    }
}