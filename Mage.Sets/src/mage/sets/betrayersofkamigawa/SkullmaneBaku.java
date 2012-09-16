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

package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public class SkullmaneBaku extends CardImpl<SkullmaneBaku> {

    private final static FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    public SkullmaneBaku(UUID ownerId) {
        super(ownerId, 83, "Skullmane Baku", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), filter, true));

        // {1}, {T}, Remove X ki counters from Skullmane Baku: Target creature gets -X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SkullmaneBakuUnboostEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SkullmaneBaku(final SkullmaneBaku card) {
        super(card);
    }

    @Override
    public SkullmaneBaku copy() {
        return new SkullmaneBaku(this);
    }
    
    class SkullmaneBakuUnboostEffect extends OneShotEffect<SkullmaneBakuUnboostEffect> {

        public SkullmaneBakuUnboostEffect() {
            super(Constants.Outcome.UnboostCreature);
            staticText = "Target creature gets -X/-X until end of turn";
        }

        public SkullmaneBakuUnboostEffect(SkullmaneBakuUnboostEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int numberToUnboost = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    numberToUnboost = ((RemoveVariableCountersSourceCost)cost).getAmount() * -1;
                }
            }
            Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
            if (creature != null && numberToUnboost != 0) {
                creature.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(numberToUnboost, numberToUnboost, Constants.Duration.EndOfTurn)), game);
            }
            return true;
        }

        @Override
        public SkullmaneBakuUnboostEffect copy() {
            return new SkullmaneBakuUnboostEffect(this);
        }

    }
}