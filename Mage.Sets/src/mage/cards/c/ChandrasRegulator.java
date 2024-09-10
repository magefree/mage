package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandrasRegulator extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Mountain card or a red card");

    static {
        filter.add(Predicates.or(
                SubType.MOUNTAIN.getPredicate(),
                new ColorPredicate(ObjectColor.RED)
        ));
    }

    public ChandrasRegulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you activate a loyalty ability of a Chandra planeswalker, you may pay {1}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(
                new DoIfCostPaid(new CopyStackObjectEffect(), new ManaCostsImpl<>("{1}")),
                SubType.CHANDRA, SetTargetPointer.SPELL));

        // {1}, {T}, Discard a Mountain card or a red card: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
        this.addAbility(ability);
    }

    private ChandrasRegulator(final ChandrasRegulator card) {
        super(card);
    }

    @Override
    public ChandrasRegulator copy() {
        return new ChandrasRegulator(this);
    }
}
