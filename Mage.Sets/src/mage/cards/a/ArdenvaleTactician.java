package mage.cards.a;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdenvaleTactician extends AdventureCard {

    public ArdenvaleTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{W}{W}",
                "Dizzying Swoop",
                new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Ardenvale Tactician
        this.getLeftHalfCard().setPT(2, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Dizzying Swoop
        // Tap up to two target creatures.
        this.getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        finalizeCard();
    }

    private ArdenvaleTactician(final ArdenvaleTactician card) {
        super(card);
    }

    @Override
    public ArdenvaleTactician copy() {
        return new ArdenvaleTactician(this);
    }
}
