package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FullSteamAhead extends CardImpl {

    public FullSteamAhead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Until end of turn, each creature you control gets +2/+2 and gains trample and "This creature can't be blocked by more than one creature."
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ).setText("until end of turn, each creature you control gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and gains trample"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()
                        .setText("this creature can't be blocked by more than one creature")),
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and \"This creature can't be blocked by more than one creature.\""));
    }

    private FullSteamAhead(final FullSteamAhead card) {
        super(card);
    }

    @Override
    public FullSteamAhead copy() {
        return new FullSteamAhead(this);
    }
}
