package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RotTideGargantua extends CardImpl {

    public RotTideGargantua(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Rot-Tide Gargantua exploits a creature, each opponent sacrifices a creature.
        this.addAbility(new ExploitCreatureTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
    }

    private RotTideGargantua(final RotTideGargantua card) {
        super(card);
    }

    @Override
    public RotTideGargantua copy() {
        return new RotTideGargantua(this);
    }
}
