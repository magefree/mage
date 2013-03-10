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
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public class QuillmaneBaku extends CardImpl<QuillmaneBaku> {

    private static final FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    public QuillmaneBaku(UUID ownerId) {
        super(ownerId, 48, "Quillmane Baku", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), filter, true));

        // {1}, Tap, Remove X ki counters from Quillmane Baku: Return target creature with converted mana cost X or less to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuillmaneBakuReturnEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public QuillmaneBaku(final QuillmaneBaku card) {
        super(card);
    }

    @Override
    public QuillmaneBaku copy() {
        return new QuillmaneBaku(this);
    }
    
    class QuillmaneBakuReturnEffect extends OneShotEffect<QuillmaneBakuReturnEffect> {

        public QuillmaneBakuReturnEffect() {
            super(Constants.Outcome.ReturnToHand);
            this.staticText = "Return target creature with converted mana cost X or less to its owner's hand";
        }

        public QuillmaneBakuReturnEffect(final QuillmaneBakuReturnEffect effect) {
            super(effect);
        }

        @Override
        public QuillmaneBakuReturnEffect copy() {
            return new QuillmaneBakuReturnEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }

            int maxConvManaCost = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    maxConvManaCost = ((RemoveVariableCountersSourceCost)cost).getAmount();
                }
            }

            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with converted mana cost " + maxConvManaCost  + " or less");
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, maxConvManaCost + 1));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                return permanent.moveToZone(Zone.HAND, source.getId(), game, false);
            }
            return false;
        }
    }
}