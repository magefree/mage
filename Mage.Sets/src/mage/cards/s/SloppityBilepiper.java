package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SloppityBilepiper extends CardImpl {

    public SloppityBilepiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Jolly Gutpipes -- {2}, {T}, Sacrifice a creature: The next creature spell you cast this turn has cascade.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new NextSpellCastHasAbilityEffect(new CascadeAbility(), StaticFilters.FILTER_CARD_CREATURE),
                new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability.withFlavorWord("Jolly Gutpipes"));
    }

    private SloppityBilepiper(final SloppityBilepiper card) {
        super(card);
    }

    @Override
    public SloppityBilepiper copy() {
        return new SloppityBilepiper(this);
    }
}
