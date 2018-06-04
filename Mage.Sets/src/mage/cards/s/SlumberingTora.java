
package mage.cards.s;

import java.util.UUID;
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

/**
 *
 * @author LevelX2
 */
public final class SlumberingTora extends CardImpl {

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
                            permanent.getSubtype(game).add(SubType.CAT);
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
