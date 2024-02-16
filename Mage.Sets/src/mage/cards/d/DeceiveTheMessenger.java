package mage.cards.d;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeceiveTheMessenger extends CardImpl {

    public DeceiveTheMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Target creature gets -3/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Amass Orcs 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ORC).concatBy("<br>"));
    }

    private DeceiveTheMessenger(final DeceiveTheMessenger card) {
        super(card);
    }

    @Override
    public DeceiveTheMessenger copy() {
        return new DeceiveTheMessenger(this);
    }
}
