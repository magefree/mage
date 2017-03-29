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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public class ArgothianPixies extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public ArgothianPixies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add("Faerie");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Argothian Pixies can't be blocked by artifact creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceByCardTypeEffect2(CardType.ARTIFACT)));
        // Prevent all damage that would be dealt to Argothian Pixies by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    public ArgothianPixies(final ArgothianPixies card) {
        super(card);
    }

    @Override
    public ArgothianPixies copy() {
        return new ArgothianPixies(this);
    }
}

class PreventDamageToSourceByCardTypeEffect2 extends PreventAllDamageToSourceEffect {

    private CardType cardType;

    public PreventDamageToSourceByCardTypeEffect2() {
        this(null);
    }

    public PreventDamageToSourceByCardTypeEffect2(CardType cardT) {
        super(Duration.WhileOnBattlefield);
        cardType = cardT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (game.getObject(event.getSourceId()).getCardType().contains(cardType)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
