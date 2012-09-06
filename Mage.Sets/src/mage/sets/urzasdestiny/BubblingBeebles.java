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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UnblockableSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;

/**
 *
 * @author Backfir3
 */
public class BubblingBeebles extends CardImpl<BubblingBeebles> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    private class DefendingPlayerControlsEnchantment implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            UUID defendingPlayer = game.getCombat().getDefendingPlayer(source.getSourceId());
            if (defendingPlayer != null) {
                return game.getBattlefield().countAll(filter, defendingPlayer, game) > 0;
            }
            return false;
        }
    }

    public BubblingBeebles(UUID ownerId) {
        super(ownerId, 29, "Bubbling Beebles", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "UDS";
        this.subtype.add("Beeble");
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bubbling Beebles is unblockable as long as defending player controls an enchantment.
        Effect effect = new ConditionalContinousEffect(new UnblockableSourceEffect(Constants.Duration.WhileOnBattlefield),
                new DefendingPlayerControlsEnchantment(),
                "{this} is unblockable as long as defending player controls an enchantment");
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
    }

    public BubblingBeebles(final BubblingBeebles card) {
        super(card);
    }

    @Override
    public BubblingBeebles copy() {
        return new BubblingBeebles(this);
    }
}
