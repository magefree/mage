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
package mage.sets.darkascension;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class CurseOfMisfortunes extends CardImpl {

    public CurseOfMisfortunes(UUID ownerId) {
        super(ownerId, 56, "Curse of Misfortunes", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Aura");
        this.subtype.add("Curse");


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // At the beginning of your upkeep, you may search your library for a Curse card that doesn't have the same name as a Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle your library.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CurseOfMisfortunesEffect(), true));
    }

    public CurseOfMisfortunes(final CurseOfMisfortunes card) {
        super(card);
    }

    @Override
    public CurseOfMisfortunes copy() {
        return new CurseOfMisfortunes(this);
    }
}

class CurseOfMisfortunesEffect extends OneShotEffect {

    public CurseOfMisfortunesEffect() {
        super(Outcome.Detriment);
        staticText = "you may search your library for a Curse card that doesn't have the same name as a Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle your library";
    }

    public CurseOfMisfortunesEffect(final CurseOfMisfortunesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("Curse card that doesn't have the same name as a Curse attached to enchanted player");
        filter.add(new SubtypePredicate("Curse"));
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player targetPlayer = game.getPlayer(enchantment.getAttachedTo());
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && targetPlayer != null) {
                // get the names of attached Curses
                for (UUID attachmentId: targetPlayer.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.getSubtype(game).contains("Curse")) {
                        filter.add(Predicates.not(new NamePredicate(attachment.getName())));
                    }
                }

                TargetCardInLibrary targetCard = new TargetCardInLibrary(filter);
                if (player.searchLibrary(targetCard, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        this.setTargetPointer(new FixedTarget(targetPlayer.getId()));
                        game.getState().setValue("attachTo:" + card.getId(), targetPlayer.getId());
                        player.shuffleLibrary(source, game);
                        return card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId());
                    }
                }
                player.shuffleLibrary(source, game);
            }
        }
        return false;
    }

    @Override
    public CurseOfMisfortunesEffect copy() {
        return new CurseOfMisfortunesEffect(this);
    }

}
