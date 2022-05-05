package mage.cards.t;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TakeToTheStreets extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CITIZEN, "");

    public TakeToTheStreets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Creatures you control get +2/+2 until end of turn. Citizens you control get an additional +1/+1 and gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, filter
        ).setText("Citizens you control get an additional +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and gain vigilance until end of turn"));
    }

    private TakeToTheStreets(final TakeToTheStreets card) {
        super(card);
    }

    @Override
    public TakeToTheStreets copy() {
        return new TakeToTheStreets(this);
    }
}
