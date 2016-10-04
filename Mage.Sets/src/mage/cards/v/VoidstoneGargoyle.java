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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Plopman
 */
public class VoidstoneGargoyle extends CardImpl {

    public VoidstoneGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add("Gargoyle");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As Voidstone Gargoyle enters the battlefield, name a nonland card.
        this.addAbility(new AsEntersBattlefieldAbility(new NameACardEffect(NameACardEffect.TypeOfName.NON_LAND_NAME)));
        // The named card can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidstoneGargoyleReplacementEffect1()));
        // Activated abilities of sources with the chosen name can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidstoneGargoyleRuleModifyingEffect2()));
    }

    public VoidstoneGargoyle(final VoidstoneGargoyle card) {
        super(card);
    }

    @Override
    public VoidstoneGargoyle copy() {
        return new VoidstoneGargoyle(this);
    }
}

class VoidstoneGargoyleReplacementEffect1 extends ContinuousRuleModifyingEffectImpl {

    public VoidstoneGargoyleReplacementEffect1() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "The named card can't be cast";
    }

    public VoidstoneGargoyleReplacementEffect1(final VoidstoneGargoyleReplacementEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VoidstoneGargoyleReplacementEffect1 copy() {
        return new VoidstoneGargoyleReplacementEffect1(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast a card with that name (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null
                    && object.getName().equals(game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY))) {
                return true;
            }
        }
        return false;
    }

}

class VoidstoneGargoyleRuleModifyingEffect2 extends ContinuousRuleModifyingEffectImpl {

    public VoidstoneGargoyleRuleModifyingEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated";
    }

    public VoidstoneGargoyleRuleModifyingEffect2(final VoidstoneGargoyleRuleModifyingEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VoidstoneGargoyleRuleModifyingEffect2 copy() {
        return new VoidstoneGargoyleRuleModifyingEffect2(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't activate abilities of sources with that name (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ACTIVATE_ABILITY) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY))) {
                return true;
            }
        }
        return false;
    }

}
