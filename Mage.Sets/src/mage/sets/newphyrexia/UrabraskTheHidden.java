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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class UrabraskTheHidden extends CardImpl<UrabraskTheHidden> {

    public UrabraskTheHidden(UUID ownerId) {
        super(ownerId, 98, "Urabrask the Hidden", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "NPH";
        this.supertype.add("Legendary");
        this.subtype.add("Praetor");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityControlledEffect(HasteAbility.getInstance(), Constants.Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures"))));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new UrabraskTheHiddenEffect()));
    }

    public UrabraskTheHidden(final UrabraskTheHidden card) {
        super(card);
    }

    @Override
    public UrabraskTheHidden copy() {
        return new UrabraskTheHidden(this);
    }
}

class UrabraskTheHiddenEffect extends ReplacementEffectImpl<UrabraskTheHiddenEffect> {
    UrabraskTheHiddenEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Tap);
        staticText = "Creatures your opponents control enter the battlefield tapped";
    }

    UrabraskTheHiddenEffect(final UrabraskTheHiddenEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = game.getPermanent(event.getTargetId());
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Card c = game.getCard(event.getTargetId());
            if (c != null && c.getCardType().contains(CardType.CREATURE))
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public UrabraskTheHiddenEffect copy() {
        return new UrabraskTheHiddenEffect(this);
    }
}