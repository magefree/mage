package mage.cards.b;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlossomingCalm extends CardImpl {

    public BlossomingCalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // You gain hexproof until your next turn. You gain 2 life.
        this.getSpellAbility().addEffect(new GainAbilityControllerEffect(
                HexproofAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("you gain hexproof until your next turn."));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private BlossomingCalm(final BlossomingCalm card) {
        super(card);
    }

    @Override
    public BlossomingCalm copy() {
        return new BlossomingCalm(this);
    }
}
