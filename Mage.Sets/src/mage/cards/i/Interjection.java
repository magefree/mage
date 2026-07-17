package mage.cards.i;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Interjection extends CardImpl {

    public Interjection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("Target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Interjection(final Interjection card) {
        super(card);
    }

    @Override
    public Interjection copy() {
        return new Interjection(this);
    }
}
