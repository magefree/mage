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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.DECLARE_ATTACKER;
import static mage.game.events.GameEvent.EventType.DECLARE_BLOCKER;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class QalSismaBehemoth extends CardImpl {

    public QalSismaBehemoth(UUID ownerId) {
        super(ownerId, 149, "Qal Sisma Behemoth", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Qal Sisma Behemoth can't attack or block unless you pay {2}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new QalSismaBehemothEffect() ));

    }

    public QalSismaBehemoth(final QalSismaBehemoth card) {
        super(card);
    }

    @Override
    public QalSismaBehemoth copy() {
        return new QalSismaBehemoth(this);
    }
}

class QalSismaBehemothEffect extends ReplacementEffectImpl {

    private static final String effectText = "{this} can't attack or block unless you pay {2}";

    QalSismaBehemothEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = effectText;
    }

    QalSismaBehemothEffect ( QalSismaBehemothEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            String chooseText;
            if (event.getType().equals(GameEvent.EventType.DECLARE_ATTACKER)) {
                chooseText = "Pay {2} to attack?";
            } else {
                chooseText = "Pay {2} to block?";
            }
            ManaCostsImpl attackBlockTax = new ManaCostsImpl("{2}");
            if (attackBlockTax.canPay(source, source.getSourceId(), event.getPlayerId(), game)
                    && player.chooseUse(Outcome.Neutral, chooseText, game)) {
                if (attackBlockTax.payOrRollback(source, game, source.getSourceId(), event.getPlayerId())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DECLARE_ATTACKER:
            case DECLARE_BLOCKER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public QalSismaBehemothEffect copy() {
        return new QalSismaBehemothEffect(this);
    }

}
