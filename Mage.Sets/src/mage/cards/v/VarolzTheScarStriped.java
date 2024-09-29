
package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.GiveScavengeContinuousEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VarolzTheScarStriped extends CardImpl {

    public VarolzTheScarStriped(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(
                new GiveScavengeContinuousEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_CARD_CREATURE)
        ));

        // Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
    }

    private VarolzTheScarStriped(final VarolzTheScarStriped card) {
        super(card);
    }

    @Override
    public VarolzTheScarStriped copy() {
        return new VarolzTheScarStriped(this);
    }
}
