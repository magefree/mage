package mage.cards.r;

import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Retrieve extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("noncreature permanent card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public Retrieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Return up to one target creature card and up to one target noncreature permanent card from your graveyard to your hand. Exile Retrieve.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setText("return up to one target creature card and up to one target " +
                        "noncreature permanent card from your graveyard to your hand"));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, filter
        ));
    }

    private Retrieve(final Retrieve card) {
        super(card);
    }

    @Override
    public Retrieve copy() {
        return new Retrieve(this);
    }
}
