package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
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
public final class DarkApostle extends CardImpl {

    public DarkApostle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Gift of Chaos -- {3}, {T}: The next noncreature spell you cast this turn has cascade.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new NextSpellCastHasAbilityEffect(new CascadeAbility(), StaticFilters.FILTER_CARD_NON_CREATURE),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.withFlavorWord("Gift of Chaos"));
    }

    private DarkApostle(final DarkApostle card) {
        super(card);
    }

    @Override
    public DarkApostle copy() {
        return new DarkApostle(this);
    }
}
