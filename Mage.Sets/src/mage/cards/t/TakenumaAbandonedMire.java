package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TakenumaAbandonedMire extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("a creature or planeswalker card from your graveyard");
    static {
        filterCard.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public TakenumaAbandonedMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // Channel — {3}{B}, Discard Takenuma, Abandoned Mire: Mill three cards, then return a creature or planeswalker card from your graveyard to your hand. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility("{3}{B}", new MillCardsControllerEffect(3));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false, filterCard, PutCards.HAND)
                .setText(", then return a creature or planeswalker card from your graveyard to your hand." +
                        " This ability costs {1} less to activate for each legendary creature you control"));
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability.addHint(LegendaryCreatureCostAdjuster.getHint()));
    }

    private TakenumaAbandonedMire(final TakenumaAbandonedMire card) {
        super(card);
    }

    @Override
    public TakenumaAbandonedMire copy() {
        return new TakenumaAbandonedMire(this);
    }
}
