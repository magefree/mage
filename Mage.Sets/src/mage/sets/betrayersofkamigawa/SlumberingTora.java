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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class SlumberingTora extends CardImpl<SlumberingTora> {

    private static final FilterCard filter = new FilterCard("Spirit or Arcane card");
    
    static {
        filter.add(Predicates.or(new SubtypePredicate("Spirit"),new SubtypePredicate("Arcane")));
    }    

    public SlumberingTora(UUID ownerId) {
        super(ownerId, 161, "Slumbering Tora", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "BOK";
        // {2}, Discard a Spirit or Arcane card: Slumbering Tora becomes an X/X Cat artifact creature until end of turn,
        // where X is the discarded card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new SlumberingToraEffect(), new ManaCostsImpl("{2}"));
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
    
    private class SlumberingToraEffect extends ContinuousEffectImpl<SlumberingToraEffect> {

        public SlumberingToraEffect() {
            super(Constants.Duration.EndOfTurn, Constants.Outcome.BecomeCreature);
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
        public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == Constants.SubLayer.NA) {
                            permanent.getCardType().add(CardType.CREATURE);
                            permanent.getSubtype().add("Cat");
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == Constants.SubLayer.SetPT_7b) {
                            int convManaCosts = 0;
                            for (Cost cost: source.getCosts()) {
                                if (cost instanceof DiscardTargetCost && ((DiscardTargetCost)cost).getCards().size() > 0) {
                                    convManaCosts = ((DiscardTargetCost)cost).getCards().get(0).getManaCost().convertedManaCost();
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
        public boolean hasLayer(Constants.Layer layer) {
            return layer == Constants.Layer.PTChangingEffects_7 || layer == Constants.Layer.TypeChangingEffects_4;
        }
    }

}
