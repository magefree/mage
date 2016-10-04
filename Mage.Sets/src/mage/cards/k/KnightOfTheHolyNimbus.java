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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOnlyByOpponentActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CantBeRegeneratedSourceEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class KnightOfTheHolyNimbus extends CardImpl {

    public KnightOfTheHolyNimbus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add("Human");
        this.subtype.add("Rebel");
        this.subtype.add("Knight");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());
        
        // If Knight of the Holy Nimbus would be destroyed, regenerate it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new KnightOfTheHolyNimbusReplacementEffect()));
        
        // {2}: Knight of the Holy Nimbus can't be regenerated this turn. Only any opponent may activate this ability.
        this.addAbility(new ActivateOnlyByOpponentActivatedAbility(Zone.BATTLEFIELD, new CantBeRegeneratedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl("{2}")));
        
    }

    public KnightOfTheHolyNimbus(final KnightOfTheHolyNimbus card) {
        super(card);
    }

    @Override
    public KnightOfTheHolyNimbus copy() {
        return new KnightOfTheHolyNimbus(this);
    }
}

class KnightOfTheHolyNimbusReplacementEffect extends ReplacementEffectImpl {

    KnightOfTheHolyNimbusReplacementEffect() {
        super(Duration.Custom, Outcome.Regenerate);
        staticText = "If {this} would be destroyed, regenerate it";
    }

    KnightOfTheHolyNimbusReplacementEffect(KnightOfTheHolyNimbusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent knightOfTheHolyNimbus = game.getPermanent(event.getTargetId());
        if (knightOfTheHolyNimbus != null 
                && event.getAmount() == 0) { // 1=noRegen
            if (knightOfTheHolyNimbus.regenerate(source.getSourceId(), game)) {
                game.informPlayers(source.getSourceObject(game).getName() + " has been regenerated.");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId() != null
                && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public KnightOfTheHolyNimbusReplacementEffect copy() {
        return new KnightOfTheHolyNimbusReplacementEffect(this);
    }

}