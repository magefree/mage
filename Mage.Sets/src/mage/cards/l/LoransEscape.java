package mage.cards.l;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoransEscape extends CardImpl {

    public LoransEscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target artifact or creature gains hexproof and indestructible until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("target artifact or creature gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
    }

    private LoransEscape(final LoransEscape card) {
        super(card);
    }

    @Override
    public LoransEscape copy() {
        return new LoransEscape(this);
    }
}
