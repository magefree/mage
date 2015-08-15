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
package mage.sets.mastersedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public class ShieldOfTheAges extends CardImpl {

    public ShieldOfTheAges(UUID ownerId) {
        super(ownerId, 165, "Shield of the Ages", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MED";

        // {2}: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShieldOfTheAgesEffect(), new GenericManaCost(2)));
    }

    public ShieldOfTheAges(final ShieldOfTheAges card) {
        super(card);
    }

    @Override
    public ShieldOfTheAges copy() {
        return new ShieldOfTheAges(this);
    }
}

class ShieldOfTheAgesEffect extends PreventionEffectImpl {

    public ShieldOfTheAgesEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "Prevent the next 1 damage that would be dealt to you this turn";
    }

    public ShieldOfTheAgesEffect(final ShieldOfTheAgesEffect effect) {
        super(effect);
    }

    @Override
    public ShieldOfTheAgesEffect copy() {
        return new ShieldOfTheAgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE,
                source.getControllerId(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            if (damage > 0) {
                event.setAmount(damage - 1);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                        source.getControllerId(), source.getSourceId(), source.getControllerId(), 1));
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}