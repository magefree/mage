
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class Foil extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Island card");
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public Foil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // You may discard an Island card and another card rather than pay Foil's mana cost.
        Ability ability = new AlternativeCostSourceAbility(new DiscardTargetCost(new TargetCardInHand(filter)));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(new FilterCard("another card"))));
        this.addAbility(ability);

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Foil(final Foil card) {
        super(card);
    }

    @Override
    public Foil copy() {
        return new Foil(this);
    }
}
