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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class GiselaBladeOfGoldnight extends CardImpl<GiselaBladeOfGoldnight> {

    public GiselaBladeOfGoldnight(UUID ownerId) {
        super(ownerId, 209, "Gisela, Blade of Goldnight", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{W}{W}");
        this.expansionSetCode = "AVR";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());

        // If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.
        // If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GiselaBladeOfGoldnightDoubleDamageEffect()));
    }

    public GiselaBladeOfGoldnight(final GiselaBladeOfGoldnight card) {
        super(card);
    }

    @Override
    public GiselaBladeOfGoldnight copy() {
        return new GiselaBladeOfGoldnight(this);
    }
}

class GiselaBladeOfGoldnightDoubleDamageEffect extends ReplacementEffectImpl<GiselaBladeOfGoldnightDoubleDamageEffect> {

    public GiselaBladeOfGoldnightDoubleDamageEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Damage);
        staticText = "If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead."
            + "If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.";
    }

    public GiselaBladeOfGoldnightDoubleDamageEffect(final GiselaBladeOfGoldnightDoubleDamageEffect effect) {
        super(effect);
    }

    @Override
    public GiselaBladeOfGoldnightDoubleDamageEffect copy() {
        return new GiselaBladeOfGoldnightDoubleDamageEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                if (event.getTargetId().equals(source.getControllerId())) {
                    preventDamage(event, source, source.getControllerId(), game);
                } else if (game.getOpponents(source.getControllerId()).contains(event.getTargetId())) {
                    event.setAmount(event.getAmount() * 2);
                }
                break;
            case DAMAGE_CREATURE:
            case DAMAGE_PLANESWALKER:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null) {
                    if (permanent.getControllerId().equals(source.getControllerId())) {
                        preventDamage(event, source, permanent.getId(), game);
                    } else if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
                        event.setAmount(event.getAmount() * 2);
                    }
                }
        }
        return false;
    }

    private void preventDamage(GameEvent event, Ability source, UUID target, Game game) {
        int amount = (int)Math.ceil(event.getAmount() / 2.0);
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, target, source.getId(), source.getControllerId(), amount, false);
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(event.getAmount() - amount);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getId(), source.getControllerId(), amount));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

}

