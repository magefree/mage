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
package mage.sets.shardsofalara;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class EmpyrialArchangel extends CardImpl<EmpyrialArchangel> {

    public EmpyrialArchangel(UUID ownerId) {
        super(ownerId, 166, "Empyrial Archangel", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{G}{W}{W}{U}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Angel");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
        // All damage that would be dealt to you is dealt to Empyrial Archangel instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EmpyrialArchangelEffect()));
    }

    public EmpyrialArchangel(final EmpyrialArchangel card) {
        super(card);
    }

    @Override
    public EmpyrialArchangel copy() {
        return new EmpyrialArchangel(this);
    }
}

class EmpyrialArchangelEffect extends ReplacementEffectImpl<EmpyrialArchangelEffect> {
    EmpyrialArchangelEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to you is dealt to {this} instead";
    }

    EmpyrialArchangelEffect(final EmpyrialArchangelEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.damage(damageEvent.getAmount(), event.getSourceId(), game, damageEvent.isPreventable(), damageEvent.isCombatDamage());
            return true;
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER && event.getPlayerId().equals(source.getControllerId()))
            return true;
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EmpyrialArchangelEffect copy() {
        return new EmpyrialArchangelEffect(this);
    }
}
