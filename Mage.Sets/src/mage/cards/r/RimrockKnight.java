package mage.cards.r;

import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RimrockKnight extends AdventureCard {

    public RimrockKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DWARF, SubType.KNIGHT}, "{1}{R}",
                "Boulder Rush",
                new CardType[]{CardType.INSTANT}, "{R}");

        // Rimrock Knight
        this.getLeftHalfCard().setPT(3, 1);

        // Rimrock Knight can't block
        this.getLeftHalfCard().addAbility(new CantBlockAbility());

        // Boulder Rush
        // Target creature gets +2/+0 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private RimrockKnight(final RimrockKnight card) {
        super(card);
    }

    @Override
    public RimrockKnight copy() {
        return new RimrockKnight(this);
    }
}
