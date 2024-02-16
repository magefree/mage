package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.AbilityResolutionCountHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InnerFlameIgniter extends CardImpl {

    public InnerFlameIgniter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{R}: Creatures you control get +1/+0 until end of turn. If this is the third time this ability has resolved this turn, creatures you control gain first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}")
        );
        ContinuousEffect effectIf3rdResolution = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(Outcome.AddAbility, 3, effectIf3rdResolution));
        ability.addHint(AbilityResolutionCountHint.instance);
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private InnerFlameIgniter(final InnerFlameIgniter card) {
        super(card);
    }

    @Override
    public InnerFlameIgniter copy() {
        return new InnerFlameIgniter(this);
    }
}