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

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class CallToTheKindred extends CardImpl<CallToTheKindred> {

    public CallToTheKindred(UUID ownerId) {
        super(ownerId, 30, "Call to the Kindred", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        // At the beginning of your upkeep, you may look at the top five cards of your library.
        // If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield,
        // then you put the rest of those cards on the bottom of your library in any order.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CallToTheKindredEffect(), true));
    }

    public CallToTheKindred(final CallToTheKindred card) {
        super(card);
    }

    @Override
    public CallToTheKindred copy() {
        return new CallToTheKindred(this);
    }
}

class CallToTheKindredEffect extends OneShotEffect<CallToTheKindredEffect> {

    public CallToTheKindredEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "look at the top five cards of your library. If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield, then you put the rest of those cards on the bottom of your library in any order";
    }

    public CallToTheKindredEffect(final CallToTheKindredEffect effect) {
        super(effect);
    }

    @Override
    public CallToTheKindredEffect copy() {
        return new CallToTheKindredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());

        if (player == null || enchantment.getAttachedTo() == null) {
            return false;
        }

        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (creature == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 5);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.lookAtCards("Call to the Kindred", cards, game);

        FilterCreatureCard filter = new FilterCreatureCard();
        StringBuilder sb = new StringBuilder("creature card with at least one subtype from: ");
        ArrayList<Predicate<MageObject>> subtypes = new ArrayList<Predicate<MageObject>>();
        for (String subtype : creature.getSubtype()) {
            subtypes.add(new SubtypePredicate(subtype));
            sb.append(subtype).append(", ");
        }
        filter.add(Predicates.or(subtypes));
        sb.delete(sb.length() - 2, sb.length());
        filter.setMessage(sb.toString());

        if (cards.count(filter, game) > 0 && player.chooseUse(Outcome.DrawCard, "Do you wish to put a creature card onto the battlefield?", game)) {
            TargetCard target = new TargetCard(Zone.PICK, filter);

            if (player.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.putOntoBattlefield(game, Zone.PICK, source.getId(), source.getControllerId());
                }
            }
        }

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}