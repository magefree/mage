
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class SedgeSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "All Sliver creatures");

    private static final FilterPermanent filterSlivers = new FilterPermanent(SubType.SLIVER, "All Slivers");

    private static final FilterPermanent filterSwamp = new FilterPermanent(SubType.SWAMP, "Swamp");

    public SedgeSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures have "This creature gets +1/+1 as long as you control a Swamp."
        Ability boost = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filterSwamp, 1, 1));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(boost, Duration.WhileOnBattlefield,
                        filter, "All Sliver creatures have \"This creature gets +1/+1 as long as you control a Swamp.\"")));
        // All Slivers have "{B}: Regenerate this permanent."
        Ability regenerate = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}"));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(regenerate, Duration.WhileOnBattlefield,
                        filterSlivers, "All Slivers have \"{B}: Regenerate this permanent.\"")));
    }

    private SedgeSliver(final SedgeSliver card) {
        super(card);
    }

    @Override
    public SedgeSliver copy() {
        return new SedgeSliver(this);
    }
}
