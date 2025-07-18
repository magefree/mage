
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ViridianScout extends CardImpl {

    public ViridianScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{G}, Sacrifice Viridian Scout: Viridian Scout deals 2 damage to target creature with flying.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2, "it"), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private ViridianScout(final ViridianScout card) {
        super(card);
    }

    @Override
    public ViridianScout copy() {
        return new ViridianScout(this);
    }
}
