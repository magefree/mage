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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TraceOfAbundance extends CardImpl<TraceOfAbundance> {
    
    private String rule = "Enchanted land has shroud";

    public TraceOfAbundance(UUID ownerId) {
        super(ownerId, 142, "Trace of Abundance", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{R/W}{G}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Aura");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land has shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ShroudAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, rule)));

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool.
        this.addAbility(new TraceOfAbundanceTriggeredAbility());
    }

    public TraceOfAbundance(final TraceOfAbundance card) {
        super(card);
    }

    @Override
    public TraceOfAbundance copy() {
        return new TraceOfAbundance(this);
    }
}

class TraceOfAbundanceTriggeredAbility extends TriggeredManaAbility<TraceOfAbundanceTriggeredAbility> {

    public TraceOfAbundanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TraceOfAbundanceEffect());
    }

    public TraceOfAbundanceTriggeredAbility(final TraceOfAbundanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TraceOfAbundanceTriggeredAbility copy() {
        return new TraceOfAbundanceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA) {
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

class TraceOfAbundanceEffect extends ManaEffect<TraceOfAbundanceEffect> {

    public TraceOfAbundanceEffect() {
        super();
        staticText = "its controller adds one mana of any color to his or her mana pool";
    }

    public TraceOfAbundanceEffect(final TraceOfAbundanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                Player player = game.getPlayer(land.getControllerId());
                if (player != null) {
                    ChoiceColor choice = new ChoiceColor();
                    while (!player.choose(outcome, choice, game) && player.isInGame()) {
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
    public TraceOfAbundanceEffect copy() {
        return new TraceOfAbundanceEffect(this);
    }
}
