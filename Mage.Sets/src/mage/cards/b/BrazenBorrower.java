package mage.cards.b;

import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrazenBorrower extends AdventureCard {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BrazenBorrower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE, SubType.ROGUE}, "{1}{U}{U}",
                "Petty Theft",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Brazen Borrower
        this.getLeftHalfCard().setPT(3, 1);

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Bazen Borrower can block only creatures with flying.
        this.getLeftHalfCard().addAbility(new CanBlockOnlyFlyingAbility());

        // Petty Theft
        // Return target nonland permanent an opponent controls to its owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private BrazenBorrower(final BrazenBorrower card) {
        super(card);
    }

    @Override
    public BrazenBorrower copy() {
        return new BrazenBorrower(this);
    }
}
