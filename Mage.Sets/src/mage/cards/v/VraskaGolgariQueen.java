package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.command.emblems.VraskaGolgariQueenEmblem;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class VraskaGolgariQueen extends CardImpl {

    private static final FilterControlledPermanent filter1
            = new FilterControlledPermanent("another permanent");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("nonland permanent with mana value 3 or less");

    static {
        filter1.add(AnotherPredicate.instance);
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public VraskaGolgariQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.setStartingLoyalty(4);

        // +2: You may sacrifice another permanent. If you do, you gain 1 life and draw a card.
        DoIfCostPaid effect = new DoIfCostPaid(
                new GainLifeEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(filter1))
        );
        effect.addEffect(new DrawCardSourceControllerEffect(1).setText("and draw a card"));
        this.addAbility(new LoyaltyAbility(effect, 2));

        // -3: Destroy target nonland permanent with converted mana cost 3 or less.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);

        // -9: You get an emblem with "Whenever a creature you control deals combat damage to a player, that player loses the game."
        this.addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new VraskaGolgariQueenEmblem()), -9
        ));
    }

    private VraskaGolgariQueen(final VraskaGolgariQueen card) {
        super(card);
    }

    @Override
    public VraskaGolgariQueen copy() {
        return new VraskaGolgariQueen(this);
    }
}
