package mage.cards.p;

import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PicklockPrankster extends AdventureCard {

    private static final FilterCard filter = new FilterCard("an instant, sorcery, or Faerie card");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.FAERIE.getPredicate()
        ));
    }

    public PicklockPrankster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE, SubType.ROGUE}, "{1}{U}",
                "Free the Fae",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Picklock Prankster
        this.getLeftHalfCard().setPT(1, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Free the Fae
        // Mill four cards. Then put an instant, sorcery, or Faerie card from among the milled cards into your hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new MillThenPutInHandEffect(4, filter, false));

        finalizeCard();
    }

    private PicklockPrankster(final PicklockPrankster card) {
        super(card);
    }

    @Override
    public PicklockPrankster copy() {
        return new PicklockPrankster(this);
    }
}
