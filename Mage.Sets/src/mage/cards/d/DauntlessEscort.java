package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class DauntlessEscort extends CardImpl {

    public DauntlessEscort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sacrifice Dauntless Escort: Creatures you control are indestructible this turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ), new SacrificeSourceCost()));
    }

    private DauntlessEscort(final DauntlessEscort card) {
        super(card);
    }

    @Override
    public DauntlessEscort copy() {
        return new DauntlessEscort(this);
    }
}
