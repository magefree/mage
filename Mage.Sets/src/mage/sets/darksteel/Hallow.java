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
package mage.sets.darksteel;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class Hallow extends CardImpl {

    public Hallow(UUID ownerId) {
        super(ownerId, 4, "Hallow", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "DST";


        // Prevent all damage target spell would deal this turn. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new HallowPreventDamageByTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    public Hallow(final Hallow card) {
        super(card);
    }

    @Override
    public Hallow copy() {
        return new Hallow(this);
    }
}

class HallowPreventDamageByTargetEffect extends PreventionEffectImpl {

    public HallowPreventDamageByTargetEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage target spell would deal this turn. You gain life equal to the damage prevented this way";
    }

    public HallowPreventDamageByTargetEffect(final HallowPreventDamageByTargetEffect effect) {
        super(effect);
    }

    @Override
    public HallowPreventDamageByTargetEffect copy() {
        return new HallowPreventDamageByTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(preventionEffectData.getPreventedDamage(), game);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject instanceof Spell){
                return this.getTargetPointer().getTargets(game, source).contains(mageObject.getId());
            }
        }
        return false;
    }

}
