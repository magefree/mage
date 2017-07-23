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
package mage.cards.c;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
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
public class CallToTheKindred extends CardImpl {

    public CallToTheKindred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");
        this.subtype.add("Aura");

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

class CallToTheKindredEffect extends OneShotEffect {

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
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());

        if (enchantment == null || controller == null || enchantment.getAttachedTo() == null) {
            return false;
        }

        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (creature == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 5));
        controller.lookAtCards(enchantment.getIdName(), cards, game);

        FilterCreatureCard filter = new FilterCreatureCard();

        if (!creature.getAbilities().contains(ChangelingAbility.getInstance())) {
            StringBuilder sb = new StringBuilder("creature card with at least one subtype from: ");
            ArrayList<Predicate<MageObject>> subtypes = new ArrayList<>();
            for (SubType subtype : creature.getSubtype(game)) {
                subtypes.add(new SubtypePredicate(subtype));
                sb.append(subtype).append(", ");
            }
            filter.add(Predicates.or(subtypes));
            sb.delete(sb.length() - 2, sb.length());
            filter.setMessage(sb.toString());
        } else {
            filter.setMessage("creature card that shares a creature type with enchanted creature");
        }

        if (cards.count(filter, game) > 0 && controller.chooseUse(Outcome.DrawCard, "Do you wish to put a creature card onto the battlefield?", source, game)) {
            TargetCard target = new TargetCard(Zone.LIBRARY, filter);

            if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
