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
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class SlumberingTora extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit or Arcane card");
    
    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.SPIRIT),new SubtypePredicate(SubType.ARCANE)));
    }    

    public SlumberingTora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        // {2}, Discard a Spirit or Arcane card: Slumbering Tora becomes an X/X Cat artifact creature until end of turn,
        // where X is the discarded card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SlumberingToraEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        this.addAbility(ability);
    }

    public SlumberingTora(final SlumberingTora card) {
        super(card);
    }

    @Override
    public SlumberingTora copy() {
        return new SlumberingTora(this);
    }
    
    private static class SlumberingToraEffect extends ContinuousEffectImpl {

        public SlumberingToraEffect() {
            super(Duration.EndOfTurn, Outcome.BecomeCreature);
            setText();
        }

        public SlumberingToraEffect(final SlumberingToraEffect effect) {
            super(effect);
        }

        @Override
        public SlumberingToraEffect copy() {
            return new SlumberingToraEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            permanent.addCardType(CardType.CREATURE);
                            permanent.getSubtype(game).add("Cat");
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            int convManaCosts = 0;
                            for (Cost cost: source.getCosts()) {
                                if (cost instanceof DiscardTargetCost && !((DiscardTargetCost) cost).getCards().isEmpty()) {
                                    convManaCosts = ((DiscardTargetCost)cost).getCards().get(0).getConvertedManaCost();
                                    break;
                                }
                            }
                            permanent.getPower().setValue(convManaCosts);
                            permanent.getToughness().setValue(convManaCosts);
                        }
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        private void setText() {
            staticText = "{this} becomes an X/X Cat artifact creature until end of turn, where X is the discarded card's converted mana cost";
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
        }
    }

}
