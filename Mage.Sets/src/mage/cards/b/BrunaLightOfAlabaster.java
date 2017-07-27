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
package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.filter.predicate.other.AuraPermanentCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;

/**
 * @author noxx
 */
public class BrunaLightOfAlabaster extends CardImpl {

    public BrunaLightOfAlabaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Bruna, Light of Alabaster attacks or blocks, you may attach to it any number of Auras on the battlefield and you may put onto the battlefield attached to it any number of Aura cards that could enchant it from your graveyard and/or hand.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BrunaLightOfAlabasterEffect(), true));
    }

    public BrunaLightOfAlabaster(final BrunaLightOfAlabaster card) {
        super(card);
    }

    @Override
    public BrunaLightOfAlabaster copy() {
        return new BrunaLightOfAlabaster(this);
    }
}

class BrunaLightOfAlabasterEffect extends OneShotEffect {

    public BrunaLightOfAlabasterEffect() {
        super(Outcome.Benefit);
        this.staticText = "attach to it any number of Auras on the battlefield and you may put onto the battlefield attached to it any number of Aura cards that could enchant it from your graveyard and/or hand";
    }

    public BrunaLightOfAlabasterEffect(final BrunaLightOfAlabasterEffect effect) {
        super(effect);
    }

    @Override
    public BrunaLightOfAlabasterEffect copy() {
        return new BrunaLightOfAlabasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID bruna = source.getSourceId();
        Player controller = game.getPlayer(source.getControllerId());

        FilterPermanent filterAura = new FilterPermanent("Aura");
        FilterCard filterAuraCard = new FilterCard("Aura card");

        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate(SubType.AURA));
        filterAura.add(new AuraPermanentCanAttachToPermanentId(bruna));
        filterAuraCard.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAuraCard.add(new SubtypePredicate(SubType.AURA));
        filterAuraCard.add(new AuraCardCanAttachToPermanentId(bruna));

        if (controller == null) {
            return false;
        }
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        List<Permanent> fromBattlefield = new ArrayList<>();
        List<Card> fromHandGraveyard = new ArrayList<>();

        int countBattlefield = game.getBattlefield().getAllActivePermanents(filterAura, game).size() - sourcePermanent.getAttachments().size();
        while (controller.canRespond()
                && countBattlefield > 0
                && controller.chooseUse(Outcome.Benefit, "Attach an Aura from the battlefield?", source, game)) {
            Target targetAura = new TargetPermanent(filterAura);
            targetAura.setNotTarget(true);
            if (controller.choose(Outcome.Benefit, targetAura, source.getSourceId(), game)) {
                Permanent aura = game.getPermanent(targetAura.getFirstTarget());
                if (aura != null) {
                    Target target = aura.getSpellAbility().getTargets().get(0);
                    if (target != null) {
                        fromBattlefield.add(aura);
                        filterAura.add(Predicates.not(new CardIdPredicate(aura.getId())));
                    }
                }
            }
            countBattlefield = game.getBattlefield().getAllActivePermanents(filterAura, game).size() - sourcePermanent.getAttachments().size();
        }

        int countHand = controller.getHand().count(filterAuraCard, game);
        while (controller.canRespond()
                && countHand > 0
                && controller.chooseUse(Outcome.Benefit, "Attach an Aura from your hand?", source, game)) {
            TargetCard targetAura = new TargetCard(Zone.HAND, filterAuraCard);
            if (controller.choose(Outcome.Benefit, controller.getHand(), targetAura, game)) {
                Card aura = game.getCard(targetAura.getFirstTarget());
                if (aura != null) {
                    Target target = aura.getSpellAbility().getTargets().get(0);
                    if (target != null) {
                        fromHandGraveyard.add(aura);
                        filterAuraCard.add(Predicates.not(new CardIdPredicate(aura.getId())));
                    }
                }
            }
            countHand = controller.getHand().count(filterAuraCard, game);
        }

        int countGraveyard = controller.getGraveyard().count(filterAuraCard, game);
        while (controller.canRespond()
                && countGraveyard > 0
                && controller.chooseUse(Outcome.Benefit, "Attach an Aura from your graveyard?", source, game)) {
            TargetCard targetAura = new TargetCard(Zone.GRAVEYARD, filterAuraCard);
            if (controller.choose(Outcome.Benefit, controller.getGraveyard(), targetAura, game)) {
                Card aura = game.getCard(targetAura.getFirstTarget());
                if (aura != null) {
                    Target target = aura.getSpellAbility().getTargets().get(0);
                    if (target != null) {
                        fromHandGraveyard.add(aura);
                        filterAuraCard.add(Predicates.not(new CardIdPredicate(aura.getId())));
                    }
                }
            }
            countGraveyard = controller.getGraveyard().count(filterAuraCard, game);
        }
        // Move permanents
        for (Permanent aura : fromBattlefield) {
            Permanent attachedTo = game.getPermanent(aura.getAttachedTo());
            if (attachedTo != null) {
                attachedTo.removeAttachment(aura.getId(), game);
            }
            sourcePermanent.addAttachment(aura.getId(), game);
        }
        // Move cards
        for (Card aura : fromHandGraveyard) {
            if (aura != null) {
                game.getState().setValue("attachTo:" + aura.getId(), sourcePermanent);
                controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                sourcePermanent.addAttachment(aura.getId(), game);
            }
        }
        return true;
    }
}
