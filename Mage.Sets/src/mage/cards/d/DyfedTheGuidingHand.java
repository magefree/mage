package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterArtifactCard;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

/**
 *
 * @author muz
 */
public final class DyfedTheGuidingHand extends CardImpl {

    public DyfedTheGuidingHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DYFED);
        this.setStartingLoyalty(4);

        // +1: Create two tapped Powerstone tokens.
        LoyaltyAbility ability = new LoyaltyAbility(new CreateTokenEffect(new PowerstoneToken(), 2, true), 1);
        this.addAbility(ability);

        // −X: Untap X target artifacts.
        LoyaltyAbility ability2 = new LoyaltyAbility(new UntapTargetEffect("untap X target artifacts"));
        ability2.addTarget(new TargetArtifactPermanent());
        ability2.setTargetAdjuster(new XTargetsCountAdjuster());
        this.addAbility(ability2);

        // −6: Search your library for an artifact card, put it onto the battlefield, then shuffle.
        LoyaltyAbility ability3 = new LoyaltyAbility(new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(new FilterArtifactCard())
        ), -6);
        this.addAbility(ability3);

        // Dyfed, the Guiding Hand can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private DyfedTheGuidingHand(final DyfedTheGuidingHand card) {
        super(card);
    }

    @Override
    public DyfedTheGuidingHand copy() {
        return new DyfedTheGuidingHand(this);
    }
}
