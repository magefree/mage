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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;

/**
 *
 * @author ayratn
 */
public class ScrapdiverSerpent extends CardImpl<ScrapdiverSerpent> {

    private static final String rule = "Scrapdiver Serpent is unblockable as long as defending player controls an artifact";

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(Constants.CardType.ARTIFACT));
    }

    private class DefendingPlayerControlsArtifact implements Condition {
        @Override
        public boolean apply(Game game, Ability source) {
            UUID defendingPlayer = game.getCombat().getDefendingPlayer(source.getSourceId());
            if (defendingPlayer != null) {
                return game.getBattlefield().countAll(filter, defendingPlayer, game) > 0;
            }
            return false;
        }
    }

    public ScrapdiverSerpent (UUID ownerId) {
        super(ownerId, 41, "Scrapdiver Serpent", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Serpent");
        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        ContinuousEffect gainAbility = new GainAbilitySourceEffect(UnblockableAbility.getInstance(), Constants.Duration.WhileOnBattlefield);
        Effect effect = new ConditionalContinousEffect(gainAbility, new DefendingPlayerControlsArtifact(), rule);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
    }

    public ScrapdiverSerpent (final ScrapdiverSerpent card) {
        super(card);
    }

    @Override
    public ScrapdiverSerpent copy() {
        return new ScrapdiverSerpent(this);
    }

}
