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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Flickerform extends CardImpl<Flickerform> {

    public Flickerform(UUID ownerId) {
        super(ownerId, 12, "Flickerform", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "C13";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {2}{W}{W}: Exile enchanted creature and all Auras attached to it. At the beginning of the next end step, return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlickerformEffect(), new ManaCostsImpl("{2}{W}{W}")));
    }

    public Flickerform(final Flickerform card) {
        super(card);
    }

    @Override
    public Flickerform copy() {
        return new Flickerform(this);
    }
}

class FlickerformEffect extends OneShotEffect<FlickerformEffect> {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();
    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public FlickerformEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile enchanted creature and all Auras attached to it. At the beginning of the next end step, return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature";
    }

    public FlickerformEffect(final FlickerformEffect effect) {
        super(effect);
    }

    @Override
    public FlickerformEffect copy() {
        return new FlickerformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Exile enchanted creature and all Auras attached to it.
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
            if (enchantedCreature != null) {
                UUID exileZoneId = UUID.randomUUID();
                enchantedCreature.moveToExile(exileZoneId, enchantment.getName(), source.getSourceId(), game);
                for (UUID attachementId: enchantedCreature.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachementId);
                    if (attachment != null && filter.match(attachment, game)) {
                        attachment.moveToExile(exileZoneId, enchantment.getName(), source.getSourceId(), game);
                    }
                }
                if (!(enchantedCreature instanceof Token)) {
                // At the beginning of the next end step, return that card to the battlefield under its owner's control.
                // If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature
                    AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(
                            new FlickerformReturnEffect(enchantedCreature.getId(), exileZoneId));
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

class FlickerformReturnEffect extends OneShotEffect<FlickerformReturnEffect> {

    private static final FilterCard filterAura = new FilterCard();
    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate("Aura"));
    }

    private final UUID enchantedCardId;
    private final UUID exileZoneId;

    public FlickerformReturnEffect(UUID enchantedCardId, UUID exileZoneId) {
        super(Outcome.Benefit);
        this.enchantedCardId = enchantedCardId;
        this.exileZoneId = exileZoneId;
        this.staticText = "return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way to the battlefield under their owners' control attached to that creature";
    }

    public FlickerformReturnEffect(final FlickerformReturnEffect effect) {
        super(effect);
        this.enchantedCardId = effect.enchantedCardId;
        this.exileZoneId = effect.exileZoneId;
    }

    @Override
    public FlickerformReturnEffect copy() {
        return new FlickerformReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        Card enchantedCard = exileZone.get(enchantedCardId, game);
        if (enchantedCard != null) {
            enchantedCard.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), enchantedCard.getOwnerId());
            Permanent newPermanent = game.getPermanent(enchantedCardId);
            if (newPermanent != null) {
                for(Card enchantment : exileZone.getCards(game)) {
                    if (filterAura.match(enchantment, game)) {
                        boolean canTarget = false;
                        for (Target target : enchantment.getSpellAbility().getTargets()) {
                            Filter filter = target.getFilter();
                            if (filter.match(newPermanent, game)) {
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
            }
            return true;
        }
        return false;
    }
}
