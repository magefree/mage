package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
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
public final class DiversionSpecialist extends CardImpl {

    public DiversionSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // {1}, Sacrifice another creature or enchantment: Exile the top card of your library. You may play it this turn.
        Ability ability = new SimpleActivatedAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
                .withTextOptions("it", true), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ANOTHER_CREATURE_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private DiversionSpecialist(final DiversionSpecialist card) {
        super(card);
    }

    @Override
    public DiversionSpecialist copy() {
        return new DiversionSpecialist(this);
    }
}
