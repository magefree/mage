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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ManaEffect;
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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public class DawnsReflection extends CardImpl {

    public DawnsReflection(UUID ownerId) {
        super(ownerId, 85, "Dawn's Reflection", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Aura");


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors to his or her mana pool.
        this.addAbility(new DawnsReflectionTriggeredAbility());
    }

    public DawnsReflection(final DawnsReflection card) {
        super(card);
    }

    @Override
    public DawnsReflection copy() {
        return new DawnsReflection(this);
    }
}
class DawnsReflectionTriggeredAbility extends TriggeredManaAbility {

    public DawnsReflectionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DawnsReflectionManaEffect());
    }

    public DawnsReflectionTriggeredAbility(final DawnsReflectionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DawnsReflectionTriggeredAbility copy() {
        return new DawnsReflectionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }


    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors to his or her mana pool <i>(in addition to the mana the land produces)<i/>.";
    }
}


class DawnsReflectionManaEffect extends ManaEffect {

    public DawnsReflectionManaEffect() {
        super();
        this.staticText = "its controller adds two mana in any combination of colors to his or her mana pool";
    }

    public DawnsReflectionManaEffect(final DawnsReflectionManaEffect effect) {
        super(effect);
    }

    @Override
    public DawnsReflectionManaEffect copy() {
        return new DawnsReflectionManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null){
                int x = 2;

            Mana mana = new Mana();
            for(int i = 0; i < x; i++){
                ChoiceColor choiceColor = new ChoiceColor();
                while (controller.canRespond() && !controller.choose(Outcome.Benefit, choiceColor, game)) {
                }

                if (choiceColor.getColor().isBlack()) {
                    mana.increaseBlack();
                } else if (choiceColor.getColor().isBlue()) {
                    mana.increaseBlue();
                } else if (choiceColor.getColor().isRed()) {
                    mana.increaseRed();
                } else if (choiceColor.getColor().isGreen()) {
                    mana.increaseGreen();
                } else if (choiceColor.getColor().isWhite()) {
                    mana.increaseWhite();
                }
            }

            controller.getManaPool().addMana(mana, game, source);
            return true;

        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }


}
