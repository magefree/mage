package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class PelakkaPredation extends ModalDoubleFacesCard {

    private static final FilterCard filter = new FilterCard("a card from it with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public PelakkaPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{B}",
                "Pelakka Caverns", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Pelakka Predation
        // Sorcery

        // Target opponent reveals their hand. You may choose a card from it with mana value 3 or greater. That player discards that card.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetOpponent());

        // 2.
        // Pelakka Caverns
        // Land

        // Pelakka Caverns enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private PelakkaPredation(final PelakkaPredation card) {
        super(card);
    }

    @Override
    public PelakkaPredation copy() {
        return new PelakkaPredation(this);
    }
}
