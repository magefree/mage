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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class BoonweaverGiant extends CardImpl {

    public BoonweaverGiant(UUID ownerId) {
        super(ownerId, 5, "Boonweaver Giant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{6}{W}");
        this.expansionSetCode = "M15";
        this.subtype.add("Giant");
        this.subtype.add("Monk");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Boonweaver Giant enters the battlefield, you may search your graveyard, hand,
        // and/or library for an Aura card and put it onto the battlefield attached to Boonweaver Giant.
        // If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoonweaverGiantEffect(), true));
    }

    public BoonweaverGiant(final BoonweaverGiant card) {
        super(card);
    }

    @Override
    public BoonweaverGiant copy() {
        return new BoonweaverGiant(this);
    }
}

class BoonweaverGiantEffect extends OneShotEffect {

    public BoonweaverGiantEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "you may search your graveyard, hand, and/or library for an Aura card and put it onto the battlefield attached to {this}. If you search your library this way, shuffle it.";
    }

    public BoonweaverGiantEffect(final BoonweaverGiantEffect effect) {
        super(effect);
    }

    @Override
    public BoonweaverGiantEffect copy() {
        return new BoonweaverGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCard filter = new FilterCard("Aura card");
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new SubtypePredicate("Aura"));

        Card card = null;
        Zone zone = null;
        if (player.chooseUse(Outcome.Neutral, "Search your graveyard for an Aura card?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (player.choose(Outcome.PutCardInPlay, player.getGraveyard(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.GRAVEYARD;
                }
            }
        }
        if (card == null && player.chooseUse(Outcome.Neutral, "Search your Hand for an Aura card?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (player.choose(Outcome.PutCardInPlay, player.getHand(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.HAND;
                }
            }
        }
        if (card == null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (player.searchLibrary(target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.LIBRARY;
                }
            }
            player.shuffleLibrary(game);
        }
        // aura card found - attach it
        if (card != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                game.getState().setValue("attachTo:" + card.getId(), permanent);
            }
            player.putOntoBattlefieldWithInfo(card, game, zone, source.getSourceId());
            if (permanent != null) {
                return permanent.addAttachment(card.getId(), game);
            }
        }
        return true;
    }
}
