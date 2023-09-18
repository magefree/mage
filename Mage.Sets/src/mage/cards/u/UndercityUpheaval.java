package mage.cards.u;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercityUpheaval extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint("Creature cards in your graveyard", xValue);

    public UndercityUpheaval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // Undergrowth -- Distribute X +1/+1 counters among any number of target creatures you control, where X is the number of creature cards in your graveyard as you cast this spell. Creatures you control gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(CounterType.P1P1, 1, "")
                .setText("distribute X +1/+1 counters among any number of target creatures you control, " +
                        "where X is the number of creature cards in your graveyard as you cast this spell"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(xValue, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.getSpellAbility().setAbilityWord(AbilityWord.UNDERGROWTH);
        this.getSpellAbility().addHint(hint);
    }

    private UndercityUpheaval(final UndercityUpheaval card) {
        super(card);
    }

    @Override
    public UndercityUpheaval copy() {
        return new UndercityUpheaval(this);
    }
}
