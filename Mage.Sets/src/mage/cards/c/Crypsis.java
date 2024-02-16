package mage.cards.c;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Crypsis extends CardImpl {

    public Crypsis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature you control gains protection from creatures your opponents control until end of turn. Untap it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new ProtectionAbility(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES),
                Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect("untap it"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private Crypsis(final Crypsis card) {
        super(card);
    }

    @Override
    public Crypsis copy() {
        return new Crypsis(this);
    }
}
