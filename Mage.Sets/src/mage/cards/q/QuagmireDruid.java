
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Loki
 */
public final class QuagmireDruid extends CardImpl {

    public QuagmireDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}, {T}, Sacrifice a creature: Destroy target enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(false), new ColoredManaCost(ColoredManaSymbol.G));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private QuagmireDruid(final QuagmireDruid card) {
        super(card);
    }

    @Override
    public QuagmireDruid copy() {
        return new QuagmireDruid(this);
    }
}
