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
package mage.sets.iceage;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class WildGrowth extends CardImpl {

    public WildGrowth(UUID ownerId) {
        super(ownerId, 165, "Wild Growth", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Aura");


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds {G} to his or her mana pool.
        this.addAbility(new WildGrowthTriggeredAbility());
    }

    public WildGrowth(final WildGrowth card) {
        super(card);
    }

    @Override
    public WildGrowth copy() {
        return new WildGrowth(this);
    }
}

class WildGrowthTriggeredAbility extends TriggeredManaAbility {


    public WildGrowthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.G), "his or her"));
    }

    public WildGrowthTriggeredAbility(final WildGrowthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WildGrowthTriggeredAbility copy() {
        return new WildGrowthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                for(Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds {G} to his or her mana pool";
    }
}
