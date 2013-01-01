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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
*
* @author LevelX2
*/
public class IllusoryAngel extends CardImpl<IllusoryAngel> {

    public IllusoryAngel(UUID ownerId) {
       super(ownerId, 19, "Illusory Angel", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
       this.expansionSetCode = "PC2";
       this.subtype.add("Angel");
       this.subtype.add("Illusion");

       this.color.setBlue(true);
       this.power = new MageInt(4);
       this.toughness = new MageInt(4);

       // Flying
       this.addAbility(FlyingAbility.getInstance());

       // Cast Illusory Angel only if you've cast another spell this turn.
       this.addAbility(new SimpleStaticAbility(Zone.ALL, new IllusoryAngelEffect()));
    }

    public IllusoryAngel(final IllusoryAngel card) {
       super(card);
    }

    @Override
    public IllusoryAngel copy() {
       return new IllusoryAngel(this);
    }
}

class IllusoryAngelEffect extends ReplacementEffectImpl<IllusoryAngelEffect> {
    IllusoryAngelEffect() {
       super(Duration.EndOfGame, Outcome.Detriment);
       staticText = "Cast Illusory Angel only if you've cast another spell this turn";
    }

    IllusoryAngelEffect(final IllusoryAngelEffect effect) {
       super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
       return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
       if (event.getType() == GameEvent.EventType.CAST_SPELL && event.getSourceId().equals(source.getSourceId())) {
           CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get("CastSpellLastTurnWatcher");
           if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) == 0) {
               return true;
           }
       }
       return false;

    }

    @Override
    public boolean apply(Game game, Ability source) {
       return true;
    }

    @Override
    public IllusoryAngelEffect copy() {
       return new IllusoryAngelEffect(this);
    }
}
