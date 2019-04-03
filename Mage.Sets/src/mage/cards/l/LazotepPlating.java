package mage.cards.l;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazotepPlating extends CardImpl {

    public LazotepPlating(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Amass 1.
        this.getSpellAbility().addEffect(new AmassEffect(1));

        // You and permanents you control gain hexproof until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("<br>You and"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ));
    }

    private LazotepPlating(final LazotepPlating card) {
        super(card);
    }

    @Override
    public LazotepPlating copy() {
        return new LazotepPlating(this);
    }
}
