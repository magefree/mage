
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class AbzanFalconer extends CardImpl {

    public AbzanFalconer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Outlast {W}
        this.addAbility(new OutlastAbility(new ColoredManaCost(ColoredManaSymbol.W)));
        
        // Each creature you control with a +1/+1 counter on it has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                FlyingAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1)
        ));
    }

    private AbzanFalconer(final AbzanFalconer card) {
        super(card);
    }

    @Override
    public AbzanFalconer copy() {
        return new AbzanFalconer(this);
    }
}
