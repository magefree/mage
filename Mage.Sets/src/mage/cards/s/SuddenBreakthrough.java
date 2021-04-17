package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuddenBreakthrough extends CardImpl {

    public SuddenBreakthrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +2/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0)
                .setText("target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn.<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Create a Treasure token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private SuddenBreakthrough(final SuddenBreakthrough card) {
        super(card);
    }

    @Override
    public SuddenBreakthrough copy() {
        return new SuddenBreakthrough(this);
    }
}
