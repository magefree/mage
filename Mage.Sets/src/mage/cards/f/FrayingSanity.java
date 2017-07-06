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
package mage.cards.f;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class FrayingSanity extends CardImpl {

    public FrayingSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add("Aura");
        this.subtype.add("Curse");

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of each end step, enchanted player puts the top X cards of his or her library into his or her graveyard, where X is the total number of cards put into his or her graveyard from anywhere this turn.
        this.addAbility(new FrayingSanityTriggeredAbility(), new CardsPutIntoGraveyardWatcher());

    }

    public FrayingSanity(final FrayingSanity card) {
        super(card);
    }

    @Override
    public FrayingSanity copy() {
        return new FrayingSanity(this);
    }
}

class FrayingSanityTriggeredAbility extends TriggeredAbilityImpl {

    public FrayingSanityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FrayingSanityEffect());
    }

    public FrayingSanityTriggeredAbility(final FrayingSanityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrayingSanityTriggeredAbility copy() {
        return new FrayingSanityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, enchanted player puts the top X cards of his or her library into his or her graveyard, where X is the total number of cards put into his or her graveyard from anywhere this turn.";
    }
}

class FrayingSanityEffect extends OneShotEffect {

    int xAmount = 0;

    public FrayingSanityEffect() {
        super(Outcome.Detriment);
        this.staticText = "";
    }

    public FrayingSanityEffect(final FrayingSanityEffect effect) {
        super(effect);
    }

    @Override
    public FrayingSanityEffect copy() {
        return new FrayingSanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player enchantedPlayer = game.getPlayer(game.getPermanent(source.getSourceId()).getAttachedTo());
        if (enchantedPlayer != null) {
            CardsPutIntoGraveyardWatcher watcher = (CardsPutIntoGraveyardWatcher) game.getState().getWatchers().get(CardsPutIntoGraveyardWatcher.class.getSimpleName());
            if (watcher != null) {
                xAmount = watcher.getAmountCardsPutToGraveyard(enchantedPlayer.getId());
            }
            Set<Card> topXCardsFromLibrary = enchantedPlayer.getLibrary().getTopCards(game, xAmount);
            return enchantedPlayer.moveCards(topXCardsFromLibrary, Zone.GRAVEYARD, source, game, false, false, true, null);
        }
        return false;
    }
}
