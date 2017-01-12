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

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author fireshoes
 */
public class AjanisAid extends CardImpl {

    private final static FilterCard filter = new FilterCard("Ajani, Valiant Protector");

    static {
        filter.add(new NamePredicate("Ajani, Valiant Protector"));
    }

    public AjanisAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");

        // When Ajani's Aid enters the battlefield, you may search your library and/or graveyard for a card named Ajani, Valiant Protector, reveal it,
        // and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));

        // Sacrifice Ajani's Aid: Prevent all combat damage a creature of your choice would deal this turn.
        Effect effect = new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn, new FilterCreaturePermanent("creature of your choice"), true);
        effect.setText("Prevent all combat damage a creature of your choice would deal this turn");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new SacrificeSourceCost()));
    }

    public AjanisAid(final AjanisAid card) {
        super(card);
    }

    @Override
    public AjanisAid copy() {
        return new AjanisAid(this);
    }
}
