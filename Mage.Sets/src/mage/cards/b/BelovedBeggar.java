package mage.cards.b;

import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BelovedBeggar extends TransformingDoubleFacedCard {

    public BelovedBeggar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{W}",
                "Generous Soul",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "W");
        this.getLeftHalfCard().setPT(0, 4);
        this.getRightHalfCard().setPT(4, 4);

        // Disturb {4}{W}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{4}{W}{W}"));

        // Generous Soul
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // If Generous Soul would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private BelovedBeggar(final BelovedBeggar card) {
        super(card);
    }

    @Override
    public BelovedBeggar copy() {
        return new BelovedBeggar(this);
    }
}
