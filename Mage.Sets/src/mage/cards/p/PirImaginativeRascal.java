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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class PirImaginativeRascal extends CardImpl {

    public PirImaginativeRascal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Partner with Toothy, Imaginary Friend (When this creature enters the battlefield, target player may put Toothy into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Toothy, Imaginary Friend", true));

        // If one or more counters would be put on a permanent your team controls, that many plus one of each of those kinds of counters are put on that permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PirImaginativeRascalEffect()));
    }

    public PirImaginativeRascal(final PirImaginativeRascal card) {
        super(card);
    }

    @Override
    public PirImaginativeRascal copy() {
        return new PirImaginativeRascal(this);
    }
}

class PirImaginativeRascalEffect extends ReplacementEffectImpl {

    PirImaginativeRascalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "If one or more counters would be put on a permanent your team controls, "
                + "that many plus one of each of those kinds of counters are put on that permanent instead";
    }

    PirImaginativeRascalEffect(final PirImaginativeRascalEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        if (amount >= 1) {
            event.setAmount(amount + 1);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        if (permanent != null && player != null && !player.hasOpponent(permanent.getControllerId(), game)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PirImaginativeRascalEffect copy() {
        return new PirImaginativeRascalEffect(this);
    }
}
