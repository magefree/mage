package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LavaRunner extends CardImpl {

    public LavaRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Lava Runner becomes the target of a spell or ability, that spell or ability's controller sacrifices a land.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_LAND_A, 1, "that spell or ability's controller"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.PLAYER, false));
    }

    private LavaRunner(final LavaRunner card) {
        super(card);
    }

    @Override
    public LavaRunner copy() {
        return new LavaRunner(this);
    }
}
