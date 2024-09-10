package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class VampireAristocrat extends CardImpl {

    public VampireAristocrat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE, SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice a creature: Vampire Aristocrat gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)));
    }

    private VampireAristocrat(final VampireAristocrat card) {
        super(card);
    }

    @Override
    public VampireAristocrat copy() {
        return new VampireAristocrat(this);
    }

}
