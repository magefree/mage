package mage.cards.o;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
public final class ObyrasAttendants extends AdventureCard {

    public ObyrasAttendants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE, SubType.WIZARD}, "{4}{U}",
                "Desperate Parry",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Obyra's Attendants
        this.getLeftHalfCard().setPT(3, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Desperate Parry
        // Target creature gets -4/-0 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-4, 0));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private ObyrasAttendants(final ObyrasAttendants card) {
        super(card);
    }

    @Override
    public ObyrasAttendants copy() {
        return new ObyrasAttendants(this);
    }
}
