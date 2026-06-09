package mage.cards.i;

import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class IntrepidTrufflesnout extends AdventureCard {

    public IntrepidTrufflesnout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BOAR}, "{1}{G}",
                "Go Hog Wild",
                new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Intrepid Trufflesnout
        this.getLeftHalfCard().setPT(3, 1);

        // Whenever Intrepid Trufflesnout attacks alone, create a Food token.
        this.getLeftHalfCard().addAbility(new AttacksAloneSourceTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Target creature gets +2/+2 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private IntrepidTrufflesnout(final IntrepidTrufflesnout card) {
        super(card);
    }

    @Override
    public IntrepidTrufflesnout copy() {
        return new IntrepidTrufflesnout(this);
    }
}
