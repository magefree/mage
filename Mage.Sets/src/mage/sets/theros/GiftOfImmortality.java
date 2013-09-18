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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class GiftOfImmortality extends CardImpl<GiftOfImmortality> {

    public GiftOfImmortality(UUID ownerId) {
        super(ownerId, 14, "Gift of Immortality", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "THS";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, return that card to the battlefield under its owner's control. 
        // Return Gift of Immortality to the battlefield attached to that creature at the beginning of the next end step.
        this.addAbility(new DiesAttachedTriggeredAbility(new GiftOfImmortalityEffect(),"enchanted creature", false));
    }

    public GiftOfImmortality(final GiftOfImmortality card) {
        super(card);
    }

    @Override
    public GiftOfImmortality copy() {
        return new GiftOfImmortality(this);
    }
}

class GiftOfImmortalityEffect extends OneShotEffect<GiftOfImmortalityEffect> {

    public GiftOfImmortalityEffect() {
        super(Outcome.Benefit);
        this.staticText = "return that card to the battlefield under its owner's control. Return {this} to the battlefield attached to that creature at the beginning of the next end step";
    }

    public GiftOfImmortalityEffect(final GiftOfImmortalityEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfImmortalityEffect copy() {
        return new GiftOfImmortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Card card = game.getCard(enchantment.getAttachedTo());
            if (card != null) {
                Zone currentZone = game.getState().getZone(card.getId());
                if (card.putOntoBattlefield(game, currentZone, source.getSourceId(), card.getOwnerId())) {
                    //create delayed triggered ability
                    Effect effect = new GiftOfImmortalityReturnEnchantmentEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(effect);
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    game.addDelayedTriggeredAbility(delayedAbility);
                }
                return true;
            }
        }



        return false;
    }
}

class GiftOfImmortalityReturnEnchantmentEffect extends OneShotEffect<GiftOfImmortalityReturnEnchantmentEffect> {

    public GiftOfImmortalityReturnEnchantmentEffect() {
        super(Outcome.BoostCreature);
        staticText = "Return {this} to the battlefield attached to that creature at the beginning of the next end step";
    }

    public GiftOfImmortalityReturnEnchantmentEffect(final GiftOfImmortalityReturnEnchantmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card aura = game.getCard(source.getSourceId());
        if (aura != null && game.getState().getZone(aura.getId()).equals(Zone.GRAVEYARD)) {
            Player you = game.getPlayer(source.getControllerId());
            Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (you != null && creature != null) {
                game.getState().setValue("attachTo:" + aura.getId(), creature);
                aura.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), you.getId());
                return creature.addAttachment(aura.getId(), game);
            }
        }

        return false;
    }

    @Override
    public GiftOfImmortalityReturnEnchantmentEffect copy() {
        return new GiftOfImmortalityReturnEnchantmentEffect(this);
    }
}
