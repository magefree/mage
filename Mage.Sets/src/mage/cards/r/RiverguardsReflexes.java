package mage.cards.r;

import mage.abilities.effects.common.UntapTargetEffect;
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
public final class RiverguardsReflexes extends CardImpl {

    public RiverguardsReflexes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 and gains first strike until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addEffect(new UntapTargetEffect("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RiverguardsReflexes(final RiverguardsReflexes card) {
        super(card);
    }

    @Override
    public RiverguardsReflexes copy() {
        return new RiverguardsReflexes(this);
    }
}
