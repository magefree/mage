package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class MyrkulsInvoker extends CardImpl {

    public MyrkulsInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Psychic Blades — {8}: Creatures you control get +2/+0 and gain menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(2, 0, Duration.EndOfTurn)
                        .setText("creatures you control get +2/+0"),
                new GenericManaCost(8)
        );
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gain menace until end of turn"));
        this.addAbility(ability.withFlavorWord("Psychic Blades"));
    }

    private MyrkulsInvoker(final MyrkulsInvoker card) {
        super(card);
    }

    @Override
    public MyrkulsInvoker copy() {
        return new MyrkulsInvoker(this);
    }
}
