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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class VerdantHaven extends CardImpl<VerdantHaven> {

    public VerdantHaven(UUID ownerId) {
        super(ownerId, 138, "Verdant Haven", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Verdant Haven enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool.
        this.addAbility(new VerdantHavenTriggeredAbility());
    }

    public VerdantHaven(final VerdantHaven card) {
        super(card);
    }

    @Override
    public VerdantHaven copy() {
        return new VerdantHaven(this);
    }
}

class VerdantHavenTriggeredAbility extends TriggeredManaAbility<VerdantHavenTriggeredAbility> {

    public VerdantHavenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VerdantHavenManaEffect());
    }

    public VerdantHavenTriggeredAbility(final VerdantHavenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VerdantHavenTriggeredAbility copy() {
        return new VerdantHavenTriggeredAbility(this);
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
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool.";
    }
}

class VerdantHavenManaEffect extends ManaEffect<VerdantHavenManaEffect> {

    public VerdantHavenManaEffect() {
        super();
        staticText = "its controller adds one mana of any color to his or her mana pool";
    }

    public VerdantHavenManaEffect(final VerdantHavenManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if(enchantment != null){
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if(land != null){
                Player player = game.getPlayer(land.getControllerId());
                if (player != null) {
                    ChoiceColor choice = new ChoiceColor();
                    while (!player.choose(outcome, choice, game)) {
                        game.debugMessage("player canceled choosing color. retrying.");
                    }
                    int amount = 1;
                    Mana mana = null;
                    if (choice.getColor().isBlack()) {
                        mana = Mana.BlackMana(amount);
                    } else if (choice.getColor().isBlue()) {
                        mana = Mana.BlueMana(amount);
                    } else if (choice.getColor().isRed()) {
                        mana = Mana.RedMana(amount);
                    } else if (choice.getColor().isGreen()) {
                        mana = Mana.GreenMana(amount);
                    } else if (choice.getColor().isWhite()) {
                        mana = Mana.WhiteMana(amount);
                    }
                    if (player != null && mana != null) {
                        player.getManaPool().addMana(mana, game, source);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public VerdantHavenManaEffect copy() {
        return new VerdantHavenManaEffect(this);
    }
}
