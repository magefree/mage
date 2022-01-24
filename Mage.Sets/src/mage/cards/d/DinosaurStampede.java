package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DinosaurStampede extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DINOSAUR, "Dinosaurs");

    public DinosaurStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Attacking creatures get +2/+0 until end of turn. Dinosaurs you control gain trample until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter));
    }

    private DinosaurStampede(final DinosaurStampede card) {
        super(card);
    }

    @Override
    public DinosaurStampede copy() {
        return new DinosaurStampede(this);
    }
}
