package mage.cards.r;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class ResistanceIsFutile extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");

    private static final FilterCard filter2
            = new FilterPermanentCard("artifact creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter2.add(CardType.ARTIFACT.getPredicate());
        filter2.add(CardType.CREATURE.getPredicate());
        filter2.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public ResistanceIsFutile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one --
        // * Destroy target nonartifact creature.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());

        // * Return target artifact creature card with mana value 3 or less from your graveyard to the battlefield.
        this.getSpellAbility().addMode(new Mode(
            new ReturnFromGraveyardToBattlefieldTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(filter2)
        ));
    }

    private ResistanceIsFutile(final ResistanceIsFutile card) {
        super(card);
    }

    @Override
    public ResistanceIsFutile copy() {
        return new ResistanceIsFutile(this);
    }
}
