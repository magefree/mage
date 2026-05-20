package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CardLeftYourGraveyardThisTurnCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CardsLeftGraveyardWatcher;

/**
 *
 * @author muz
 */
public final class PrimaryResearch extends CardImpl {

    private static final FilterPermanentCard filterCard = new FilterPermanentCard("nonland permanent card with mana value 3 or less from your graveyard");

    static {
        filterCard.add(Predicates.not(CardType.LAND.getPredicate()));
        filterCard.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public PrimaryResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When this enchantment enters, return target nonland permanent card with mana value 3 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filterCard));
        this.addAbility(ability);

        // At the beginning of your end step, if a card left your graveyard this turn, draw a card.
        this.addAbility(
            new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU,
                new DrawCardSourceControllerEffect(1),
                false,
                CardLeftYourGraveyardThisTurnCondition.instance
            ),
            new CardsLeftGraveyardWatcher()
        );
    }

    private PrimaryResearch(final PrimaryResearch card) {
        super(card);
    }

    @Override
    public PrimaryResearch copy() {
        return new PrimaryResearch(this);
    }
}
