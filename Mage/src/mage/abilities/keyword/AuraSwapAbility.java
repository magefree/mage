/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Mael
 */
public class AuraSwapAbility extends ActivatedAbilityImpl {

    public AuraSwapAbility(ManaCost manaCost) {
        super(Zone.BATTLEFIELD, new AuraSwapEffect(), manaCost);

    }

    public AuraSwapAbility(final AuraSwapAbility ability) {
        super(ability);
    }

    @Override
    public AuraSwapAbility copy() {
        return new AuraSwapAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Aura swap ").append(getManaCostsToPay().getText()).append(" <i>(")
                .append(getManaCostsToPay().getText())
                .append(": Exchange this Aura with an Aura card in your hand.)</i>").toString();
    }
}

class AuraSwapEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    AuraSwapEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exchange this Aura with an Aura card in your hand.";
    }

    AuraSwapEffect(final AuraSwapEffect effect) {
        super(effect);
    }

    @Override
    public AuraSwapEffect copy() {
        return new AuraSwapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent auraPermanent = game.getPermanent(source.getSourceId());
            if (auraPermanent != null && auraPermanent.getSubtype().contains("Aura") && auraPermanent.getOwnerId().equals(source.getControllerId())) {
                Permanent enchantedPermanent = game.getPermanent(auraPermanent.getAttachedTo());
                filter.add(new AuraCardCanAttachToPermanentId(enchantedPermanent.getId()));
                TargetCardInHand target = new TargetCardInHand(0, 1, filter);
                if (controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                    Card auraInHand = game.getCard(target.getFirstTarget());
                    if (auraInHand != null) {
                        controller.putOntoBattlefieldWithInfo(auraInHand, game, Zone.HAND, source.getSourceId());
                        enchantedPermanent.addAttachment(auraInHand.getId(), game);
                        controller.moveCards(auraPermanent, null, Zone.HAND, source, game);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
