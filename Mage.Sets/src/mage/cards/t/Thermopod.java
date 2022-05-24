
package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 *
 * @author LoneFox
 */
public final class Thermopod extends CardImpl {

    public Thermopod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {S}: Thermopod gains haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{S}")));
        // Sacrifice a creature: Add {R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(1), CreaturesYouControlCount.instance),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT))));
    }

    private Thermopod(final Thermopod card) {
        super(card);
    }

    @Override
    public Thermopod copy() {
        return new Thermopod(this);
    }
}
