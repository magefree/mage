package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrashingDrawbridge extends CardImpl {

    public CrashingDrawbridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Creatures you control gain haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ), new TapSourceCost()));
    }

    private CrashingDrawbridge(final CrashingDrawbridge card) {
        super(card);
    }

    @Override
    public CrashingDrawbridge copy() {
        return new CrashingDrawbridge(this);
    }
}
