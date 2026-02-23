package mage.cards.c;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetousCastaway extends TransformingDoubleFacedCard {

    public CovetousCastaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{1}{U}",
                "Ghostly Castigator",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "U");


        // Covetous Castaway
        this.getLeftHalfCard().setPT(1, 3);

        // When Covetous Castaway dies, mill three cards.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new MillCardsControllerEffect(3)));

        // Disturb {3}{U}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{U}{U}"));

        // Ghostly Castigator
        this.getRightHalfCard().setPT(3, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // When Ghostly Castigator enters the battlefield, you may shuffle up to three target cards from your graveyard into your library.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ShuffleIntoLibraryTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(0, 3));
        this.getRightHalfCard().addAbility(ability);

        // If Ghostly Castigator would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private CovetousCastaway(final CovetousCastaway card) {
        super(card);
    }

    @Override
    public CovetousCastaway copy() {
        return new CovetousCastaway(this);
    }
}
