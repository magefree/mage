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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public class FertileGround extends CardImpl<FertileGround> {

    public FertileGround(UUID ownerId) {
        super(ownerId, 252, "Fertile Ground", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "USG";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool.
        this.addAbility(new FertileGroundTriggeredAbility());
    }

    public FertileGround(final FertileGround card) {
        super(card);
    }

    @Override
    public FertileGround copy() {
        return new FertileGround(this);
    }
}

class FertileGroundTriggeredAbility extends TriggeredManaAbility<FertileGroundTriggeredAbility> {

   
    public FertileGroundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect());
        this.addChoice(new ChoiceColor());
    }

    public FertileGroundTriggeredAbility(FertileGroundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if(event.getType() == GameEvent.EventType.TAPPED_FOR_MANA){
            if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }  
        return false;
    }

    @Override
    public FertileGroundTriggeredAbility copy() {
        return new FertileGroundTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool";
    }
}