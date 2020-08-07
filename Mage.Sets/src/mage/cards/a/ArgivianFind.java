package mage.cards.a;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ArgivianFind extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or enchantment card from your graveyard");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public ArgivianFind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Return target artifact or enchantment card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
    }

    private ArgivianFind(final ArgivianFind card) {
        super(card);
    }

    @Override
    public ArgivianFind copy() {
        return new ArgivianFind(this);
    }
}
