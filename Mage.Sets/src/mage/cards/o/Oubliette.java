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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author MarcoMarin
 */
public class Oubliette extends CardImpl {

    public Counters godHelpMe = null;

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature");

    public Oubliette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // When Oubliette enters the battlefield, exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new OublietteEffect(), false);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // When Oubliette leaves the battlefield, return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it. If you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new OublietteReturnEffect(), false);
        this.addAbility(ability2);
    }

    public Oubliette(final Oubliette card) {
        super(card);
    }

    @Override
    public Oubliette copy() {
        return new Oubliette(this);
    }
}

class OublietteEffect extends OneShotEffect {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public OublietteEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature";
    }

    public OublietteEffect(final OublietteEffect effect) {
        super(effect);
    }

    @Override
    public OublietteEffect copy() {
        return new OublietteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Exile enchanted creature and all Auras attached to it.
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        UUID targetId = source.getFirstTarget();

        if (targetId == null) {
            return false; // if previous scan somehow failed, simply quit
        }
        if (enchantment != null) { //back to code (mostly) copied from Flickerform
            Permanent enchantedCreature = game.getPermanent(targetId);
            if (enchantedCreature != null) {
                UUID exileZoneId = source.getSourceId();
                enchantedCreature.moveToExile(exileZoneId, enchantment.getName(), source.getSourceId(), game);
                for (UUID attachementId : enchantedCreature.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachementId);
                    if (attachment != null && filter.match(attachment, game)) {
                        attachment.moveToExile(exileZoneId, enchantment.getName(), source.getSourceId(), game);
                    }
                }

                //((Oubliette)enchantment.getMainCard()).godHelpMe = enchantedCreature.getCounters(game); //why doesn't work? should return the same card, no?
                ((Oubliette) game.getCard(source.getSourceId())).godHelpMe = enchantedCreature.getCounters(game).copy();
                /*
                if (!(enchantedCreature instanceof Token)) {

                    // If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature
                    LeavesBattlefieldTriggeredAbility triggeredAbility = new LeavesBattlefieldTriggeredAbility(
                            new OublietteReturnEffect(), false);
                    //enchantment.addAbility(triggeredAbility, source.getSourceId(), game, false);
                    //Card card = game.getCard(source.getSourceId());
                    //game.getState().addOtherAbility(card, triggeredAbility);


                }*/
                return true;
            }
        }

        return false;
    }
}

class OublietteReturnEffect extends OneShotEffect {

    private static final FilterCard filterAura = new FilterCard();

    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate("Aura"));
    }

    public OublietteReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it. If you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent";

    }

    public OublietteReturnEffect(final OublietteReturnEffect effect) {
        super(effect);
    }

    @Override
    public OublietteReturnEffect copy() {
        return new OublietteReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(source.getSourceId());

        FilterCard filter = new FilterCard();
        filter.add(new CardTypePredicate(CardType.CREATURE));
        //There should be only 1 there, but the for each loop seems the most practical to get to it
        for (Card enchantedCard : exileZone.getCards(filter, game)) {
            if (enchantedCard == null) {
                continue;
            }
            enchantedCard.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), enchantedCard.getOwnerId());
            Permanent newPermanent = game.getPermanent(enchantedCard.getId());
            if (newPermanent != null) {
                newPermanent.tap(game);
                for (Card enchantment : exileZone.getCards(game)) {
                    if (filterAura.match(enchantment, game)) {
                        boolean canTarget = false;
                        for (Target target : enchantment.getSpellAbility().getTargets()) {
                            Filter filter2 = target.getFilter();
                            if (filter2.match(newPermanent, game)) {
                                canTarget = true;
                                break;
                            }
                        }
                        if (!canTarget) {
                            // Aura stays exiled
                            continue;
                        }
                        game.getState().setValue("attachTo:" + enchantment.getId(), newPermanent);
                    }
                    if (enchantment.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), enchantment.getOwnerId())) {
                        if (filterAura.match(enchantment, game)) {
                            newPermanent.addAttachment(enchantment.getId(), game);
                        }
                    }
                }
                Card oubliette = game.getCard(source.getSourceId());
                if (oubliette == null) {
                    return false;//1st stab at getting those counters back
                }
                for (Counter c : ((Oubliette) oubliette).godHelpMe.values()) { //would be nice if could just use that copy function to set the whole field
                    if (c != null) {
                        newPermanent.getCounters(game).addCounter(c);
                    }
                }

            }
            return true;
        }

        return false;
    }
}
