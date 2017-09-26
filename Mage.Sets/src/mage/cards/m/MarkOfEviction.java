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
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class MarkOfEviction extends CardImpl {

    public MarkOfEviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, return enchanted creature and all Auras attached to that creature to their owners' hands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MarkOfEvictionEffect(), TargetController.YOU, false));
    }

    public MarkOfEviction(final MarkOfEviction card) {
        super(card);
    }

    @Override
    public MarkOfEviction copy() {
        return new MarkOfEviction(this);
    }
}

class MarkOfEvictionEffect extends OneShotEffect {

    public MarkOfEvictionEffect() {
        super(Outcome.Benefit);
        this.staticText = "return enchanted creature and all Auras attached to that creature to their owners' hands";
    }

    public MarkOfEvictionEffect(final MarkOfEvictionEffect effect) {
        super(effect);
    }

    @Override
    public MarkOfEvictionEffect copy() {
        return new MarkOfEvictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null && sourceObject.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(sourceObject.getAttachedTo());
            if (enchanted != null) {
                Set<Card> toHand = new HashSet<>();
                toHand.add(enchanted);
                for (UUID attachmentId : enchanted.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.hasSubtype(SubType.AURA, game)) {
                        toHand.add(attachment);
                    }
                }
                controller.moveCards(toHand, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
