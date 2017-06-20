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
package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class QuillmaneBaku extends CardImpl {

    private static final FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();
    private final UUID originalId;

    public QuillmaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), filter, true));

        // {1}, Tap, Remove X ki counters from Quillmane Baku: Return target creature with converted mana cost X or less to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuillmaneBakuReturnEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            int maxConvManaCost = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    maxConvManaCost = ((RemoveVariableCountersSourceCost) cost).getAmount();
                }
            }
            ability.getTargets().clear();
            FilterCreaturePermanent newFilter = new FilterCreaturePermanent("creature with converted mana cost " + maxConvManaCost + " or less");
            newFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, maxConvManaCost + 1));
            TargetCreaturePermanent target = new TargetCreaturePermanent(newFilter);
            ability.getTargets().add(target);
        }
    }

    public QuillmaneBaku(final QuillmaneBaku card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public QuillmaneBaku copy() {
        return new QuillmaneBaku(this);
    }

    static class QuillmaneBakuReturnEffect extends OneShotEffect {

        public QuillmaneBakuReturnEffect() {
            super(Outcome.ReturnToHand);
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
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                controller.moveCards(permanent, Zone.HAND, source, game);
            }
            return true;
        }
    }
}
