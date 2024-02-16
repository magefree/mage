
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SlumberingTora extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit or Arcane card");

    static {
        filter.add(Predicates.or(SubType.SPIRIT.getPredicate(), SubType.ARCANE.getPredicate()));
    }

    public SlumberingTora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, Discard a Spirit or Arcane card: Slumbering Tora becomes an X/X Cat artifact creature until end of turn,
        //                                       where X is the discarded card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(new SlumberingToraEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        this.addAbility(ability);
    }

    private SlumberingTora(final SlumberingTora card) {
        super(card);
    }

    @Override
    public SlumberingTora copy() {
        return new SlumberingTora(this);
    }
}

class SlumberingToraEffect extends ContinuousEffectImpl {

    private int convManaCosts = 0;

    SlumberingToraEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "{this} becomes an X/X Cat artifact creature until end of turn, " +
                "where X is the discarded card's mana value";
    }

    private SlumberingToraEffect(final SlumberingToraEffect effect) {
        super(effect);
        this.convManaCosts = effect.convManaCosts;
    }

    @Override
    public SlumberingToraEffect copy() {
        return new SlumberingToraEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Cost cost : source.getCosts()) {
            if (cost instanceof DiscardTargetCost && !((DiscardTargetCost) cost).getCards().isEmpty()) {
                convManaCosts = ((DiscardTargetCost) cost).getCards().get(0).getManaValue();
                return;
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.addSubType(game, SubType.CAT);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(convManaCosts);
                    permanent.getToughness().setModifiedBaseValue(convManaCosts);
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}