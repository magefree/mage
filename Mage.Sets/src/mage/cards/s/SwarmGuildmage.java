package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class SwarmGuildmage extends CardImpl {

    public SwarmGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{B}, {T}: Creatures you control get +1/+0 and gain menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+0"),
                new ManaCostsImpl<>("{4}{B}")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and gain menace until end of turn. " +
                "<i>(They can't be blocked except by two or more creatures.)</i>"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {1}{G}, {T}: You gain 2 life.
        ability = new SimpleActivatedAbility(
                new GainLifeEffect(2),
                new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SwarmGuildmage(final SwarmGuildmage card) {
        super(card);
    }

    @Override
    public SwarmGuildmage copy() {
        return new SwarmGuildmage(this);
    }
}
