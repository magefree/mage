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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CowedByWisdom extends CardImpl {

    public CowedByWisdom(UUID ownerId) {
        super(ownerId, 5, "Cowed by Wisdom", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CowedByWisdomEffect()));
    }

    public CowedByWisdom(final CowedByWisdom card) {
        super(card);
    }

    @Override
    public CowedByWisdom copy() {
        return new CowedByWisdom(this);
    }
}

class CowedByWisdomEffect extends ReplacementEffectImpl {

    private static final String effectText = "Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand";

    CowedByWisdomEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = effectText;
    }

    CowedByWisdomEffect ( CowedByWisdomEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKER || event.getType().equals(GameEvent.EventType.DECLARE_BLOCKER);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(event.getSourceId());
        return enchantment != null && enchantment.getAttachments().contains(source.getSourceId());
    }
    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            if (controller.getHand().isEmpty()) {
                return false;
            }
            String chooseText;
            int cardsInHand = controller.getHand().size();
            if (event.getType().equals(GameEvent.EventType.DECLARE_ATTACKER)) {
                chooseText = "Pay {" + cardsInHand + "} to attack?";
            } else {
                chooseText = "Pay {" + cardsInHand + "} to block?";
            }
            ManaCostsImpl attackBlockTax = new ManaCostsImpl("{" + cardsInHand + "}");
            if (attackBlockTax.canPay(source, source.getSourceId(), event.getPlayerId(), game)
                    && player.chooseUse(Outcome.Neutral, chooseText, source, game)) {
                if (attackBlockTax.payOrRollback(source, game, source.getSourceId(), event.getPlayerId())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public CowedByWisdomEffect copy() {
        return new CowedByWisdomEffect(this);
    }

}
