
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AbzanBattlePriest extends CardImpl {

    public AbzanBattlePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Outlast {W}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{W}")));

        // Each creature you control with a +1/+1 counter on it has lifelink.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        LifelinkAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1,
                        "Each creature you control with a +1/+1 counter on it has lifelink"
                )
        ));
    }

    private AbzanBattlePriest(final AbzanBattlePriest card) {
        super(card);
    }

    @Override
    public AbzanBattlePriest copy() {
        return new AbzanBattlePriest(this);
    }
}
