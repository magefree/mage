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
package mage.sets.thedark;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author MarcoMarin
 */
public class UncleIstvan extends CardImpl {

    public UncleIstvan(UUID ownerId) {
        super(ownerId, 16, "Uncle Istvan", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");
        this.expansionSetCode = "DRK";
        this.subtype.add("Human");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prevent all damage that would be dealt to Uncle Istvan by creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceByCardTypeEffect(CardType.CREATURE)));
    }

    public UncleIstvan(final UncleIstvan card) {
        super(card);
    }

    @Override
    public UncleIstvan copy() {
        return new UncleIstvan(this);
    }
}

class PreventDamageToSourceByCardTypeEffect extends PreventAllDamageToSourceEffect {
    
    private CardType cardType;
      
    public PreventDamageToSourceByCardTypeEffect(){
        this(null);
    }

    public PreventDamageToSourceByCardTypeEffect(CardType cardT){
        super(Duration.WhileOnBattlefield);
        cardType = cardT;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (game.getObject(event.getSourceId()).getCardType().contains(cardType)){
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }
}