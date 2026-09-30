package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author muz
 */
public final class SolitaryCell extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls with mana value 3 or less");
    private static final FilterCard filterCard = new FilterCard("a legendary card");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
        filterCard.add(SuperType.LEGENDARY.getPredicate());
    }

    public SolitaryCell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}{W}");

        // When this artifact enters, exile target nonland permanent an opponent controls with mana value 3 or less until this artifact leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {1}, {T}, Discard a legendary card: Draw a card.
        Ability activatedAbility = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{1}")
        );
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addCost(new DiscardTargetCost(new TargetCardInHand(filterCard)));
        this.addAbility(activatedAbility);
    }

    private SolitaryCell(final SolitaryCell card) {
        super(card);
    }

    @Override
    public SolitaryCell copy() {
        return new SolitaryCell(this);
    }
}
