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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public class EssenceOfTheWild extends CardImpl {

    public EssenceOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}");
        this.subtype.add("Avatar");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Creatures you control enter the battlefield as a copy of Essence of the Wild.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EssenceOfTheWildEffect()));
    }

    public EssenceOfTheWild(final EssenceOfTheWild card) {
        super(card);
    }

    @Override
    public EssenceOfTheWild copy() {
        return new EssenceOfTheWild(this);
    }
}

class EssenceOfTheWildEffect extends ReplacementEffectImpl {

    public EssenceOfTheWildEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy, false);
        staticText = "Creatures you control enter the battlefield as a copy of {this}";
    }

    public EssenceOfTheWildEffect(EssenceOfTheWildEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent perm = ((EntersTheBattlefieldEvent) event).getTarget();
        return perm != null && perm.getCardType().contains(CardType.CREATURE) && perm.getControllerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            Permanent permanentReset = sourceObject.copy();
            permanentReset.getCounters(game).clear();
            permanentReset.getPower().resetToBaseValue();
            permanentReset.getToughness().resetToBaseValue();
            game.addEffect(new CopyEffect(Duration.Custom, permanentReset, event.getTargetId()), source);
        }
        return false;
    }

    @Override
    public EssenceOfTheWildEffect copy() {
        return new EssenceOfTheWildEffect(this);
    }

}
